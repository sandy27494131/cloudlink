
package com.winit.cloudlink.common.extension;

import com.winit.cloudlink.common.extension.ext1.SimpleExt;
import com.winit.cloudlink.common.extension.ext1.impl.SimpleExtImpl1;
import com.winit.cloudlink.common.extension.ext1.impl.SimpleExtImpl2;
import com.winit.cloudlink.common.extension.ext2.NoDefaultExt;
import com.winit.cloudlink.common.extension.ext3.WrappedExt;
import com.winit.cloudlink.common.extension.ext3.impl.Ext3Impl1;
import com.winit.cloudlink.common.extension.ext3.impl.Ext3Impl2;
import com.winit.cloudlink.common.extension.ext3.impl.Ext3Wrapper1;
import com.winit.cloudlink.common.extension.ext3.impl.Ext3Wrapper2;
import com.winit.cloudlink.common.extension.ext4.WithAttributeExt;
import com.winit.cloudlink.common.extension.ext5.ImplNoDefaultConstructorExt;
import com.winit.cloudlink.common.extension.ext6.InjectExt;
import com.winit.cloudlink.common.extension.ext6.impl.Ext6Impl2;
import com.winit.cloudlink.common.extension.ext7.InitErrorExt;
import com.winit.cloudlink.common.extension.ext8.InvalidNameExt;
import com.winit.cloudlink.common.extension.ext8.InvalidNameExt2;
import com.winit.cloudlink.util.Utils;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.containsString;

/**
 * @author stvliu
 */
public class ExtensionLoaderTest {
    @Test
    public void test_getExtensionLoader_Null() throws Exception {
        try {
            ExtensionLoader.getExtensionLoader(null);
            fail();
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage(),
                    containsString("Extension type == null"));
        }
    }

    @Test
    public void test_getExtensionLoader_NotInterface() throws Exception {
        try {
            ExtensionLoader.getExtensionLoader(ExtensionLoaderTest.class);
            fail();
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage(),
                    containsString("type(com.winit.cloudlink.common.extension.ExtensionLoaderTest) is not interface"));
        }
    }

    @Test
    public void test_getExtensionLoader_NotSpiAnnotation() throws Exception {
        try {
            ExtensionLoader.getExtensionLoader(NoExtensionExt.class);
            fail();
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage(),
                    allOf(containsString(NoExtensionExt.class.getName()),
                            containsString("is not a extension"),
                            containsString("WITHOUT @Extension Annotation")));
        }
    }

    @Test
    public void test_getDefault() throws Exception {
        SimpleExt ext = ExtensionLoader.getExtensionLoader(SimpleExt.class).getDefaultExtension();
        assertThat(ext, instanceOf(SimpleExtImpl1.class));

        String name = ExtensionLoader.getExtensionLoader(SimpleExt.class).getDefaultExtensionName();
        assertEquals("impl1", name);
    }

    @Test
    public void test_getDefault_NULL() throws Exception {
        try {
            ExtensionLoader.getExtensionLoader(NoDefaultExt.class).getDefaultExtension();
        } catch (IllegalStateException expected) {
            assertThat(expected.getMessage(), containsString("No default extension on extension com.winit.cloudlink.common.extension.ext2.NoDefaultExt"));
        }


        String name = ExtensionLoader.getExtensionLoader(NoDefaultExt.class).getDefaultExtensionName();
        assertNull(name);
    }

    @Test
    public void test_getExtension() throws Exception {
        assertTrue(ExtensionLoader.getExtensionLoader(SimpleExt.class).getExtension("impl1") instanceof SimpleExtImpl1);
        assertTrue(ExtensionLoader.getExtensionLoader(SimpleExt.class).getExtension("impl2") instanceof SimpleExtImpl2);
    }

    @Test
    public void test_getExtension_DifferentInstance() throws Exception {
        ExtensionLoader<SimpleExt> extensionLoader = ExtensionLoader.getExtensionLoader(SimpleExt.class);
        assertNotSame(extensionLoader.getExtension("impl1"), extensionLoader.getExtension("impl1"));
    }

    @Test
    public void test_getExtension_WithWrapper_notAutoLoad() throws Exception {
        //
        WrappedExt impl1 = ExtensionLoader.getExtensionLoader(WrappedExt.class).getExtension("impl1", new ArrayList<String>());
        assertThat(impl1, instanceOf(Ext3Impl1.class));

        WrappedExt impl2 = ExtensionLoader.getExtensionLoader(WrappedExt.class).getExtension("impl2", new ArrayList<String>());
        assertThat(impl2, instanceOf(Ext3Impl2.class));

        int echoCount1 = Ext3Wrapper1.echoCount.get();
        int echoCount2 = Ext3Wrapper2.echoCount.get();
        int yellCount1 = Ext3Wrapper1.yellCount.get();
        int yellCount2 = Ext3Wrapper2.yellCount.get();

        Map<String, String> config = Utils.kv2Map("protocol", "p1", "host", "1.2.3.4", "port", "1010", "path", "path1");

        assertEquals("Ext3Impl1-echo", impl1.echo(config, "ha"));
        assertEquals(echoCount1, Ext3Wrapper1.echoCount.get());
        assertEquals(echoCount2, Ext3Wrapper2.echoCount.get());
        assertEquals(yellCount1, Ext3Wrapper1.yellCount.get());
        assertEquals(yellCount2, Ext3Wrapper2.yellCount.get());

        assertEquals("Ext3Impl2-yell", impl2.yell(config, "ha"));
        assertEquals(echoCount1, Ext3Wrapper1.echoCount.get());
        assertEquals(echoCount2, Ext3Wrapper2.echoCount.get());
        assertEquals(yellCount1, Ext3Wrapper1.yellCount.get());
        assertEquals(yellCount2, Ext3Wrapper2.yellCount.get());
    }

    @Test
    public void test_getExtension_WithWrapper() throws Exception {
        WrappedExt impl1 = ExtensionLoader.getExtensionLoader(WrappedExt.class).getExtension("impl1", Arrays.asList("wrapper1", "wrapper2"));
        assertThat(impl1, anyOf(instanceOf(Ext3Wrapper1.class), instanceOf(Ext3Wrapper2.class)));

        WrappedExt impl2 = ExtensionLoader.getExtensionLoader(WrappedExt.class).getExtension("impl2", Arrays.asList("wrapper1", "wrapper2"));
        assertThat(impl2, anyOf(instanceOf(Ext3Wrapper1.class), instanceOf(Ext3Wrapper2.class)));

        int echoCount1 = Ext3Wrapper1.echoCount.get();
        int echoCount2 = Ext3Wrapper2.echoCount.get();
        int yellCount1 = Ext3Wrapper1.yellCount.get();
        int yellCount2 = Ext3Wrapper2.yellCount.get();

        Map<String, String> config = Utils.kv2Map("protocol", "p1", "host", "1.2.3.4", "port", "1010", "path", "path1");

        assertEquals("Ext3Impl1-echo", impl1.echo(config, "ha"));
        assertEquals(echoCount1 + 1, Ext3Wrapper1.echoCount.get());
        assertEquals(echoCount2 + 1, Ext3Wrapper2.echoCount.get());
        assertEquals(yellCount1, Ext3Wrapper1.yellCount.get());
        assertEquals(yellCount2, Ext3Wrapper2.yellCount.get());

        assertEquals("Ext3Impl2-yell", impl2.yell(config, "ha"));
        assertEquals(echoCount1 + 1, Ext3Wrapper1.echoCount.get());
        assertEquals(echoCount2 + 1, Ext3Wrapper2.echoCount.get());
        assertEquals(yellCount1 + 1, Ext3Wrapper1.yellCount.get());
        assertEquals(yellCount2 + 1, Ext3Wrapper2.yellCount.get());
    }

    @Test
    public void test_getExtension_ExceptionNoExtension() throws Exception {
        try {
            ExtensionLoader.getExtensionLoader(SimpleExt.class).getExtension("NotExisted");
            fail();
        } catch (IllegalStateException expected) {
            assertThat(expected.getMessage(), containsString("No such extension com.winit.cloudlink.common.extension.ext1.SimpleExt by name NotExisted"));
        }
    }

    @Test
    public void test_getExtension_ExceptionNoExtension_NameOnWrapperNoEffective() throws Exception {
        try {
            ExtensionLoader.getExtensionLoader(WrappedExt.class).getExtension("wrapper1");
            fail();
        } catch (IllegalStateException expected) {
            assertThat(expected.getMessage(), containsString("No such extension com.winit.cloudlink.common.extension.ext3.WrappedExt by name wrapper1"));
        }
    }

    @Test
    public void test_getExtension_ExceptionNullArg() throws Exception {
        try {
            ExtensionLoader.getExtensionLoader(SimpleExt.class).getExtension((String) null);
            fail();
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage(), containsString("Extension name == null"));
        }
    }

    @Test
    public void test_hasExtension() throws Exception {
        assertTrue(ExtensionLoader.getExtensionLoader(SimpleExt.class).hasExtension("impl1"));
        assertFalse(ExtensionLoader.getExtensionLoader(SimpleExt.class).hasExtension("impl1,impl2"));
        assertFalse(ExtensionLoader.getExtensionLoader(SimpleExt.class).hasExtension("xxx"));

        try {
            ExtensionLoader.getExtensionLoader(SimpleExt.class).hasExtension(null);
            fail();
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage(), containsString("Extension name == null"));
        }
    }

    @Test
    public void test_hasDefaultExtension() throws Exception {
        assertTrue(ExtensionLoader.getExtensionLoader(SimpleExt.class).hasDefaultExtension());
        assertFalse(ExtensionLoader.getExtensionLoader(NoDefaultExt.class).hasDefaultExtension());
    }

    @Test
    public void test_hasExtension_wrapperIsNotExt() throws Exception {
        ExtensionLoader<WrappedExt> extensionLoader = ExtensionLoader.getExtensionLoader(WrappedExt.class);

        assertTrue(extensionLoader.hasExtension("impl1"));
        assertFalse(extensionLoader.hasExtension("impl1,impl2"));
        assertFalse(extensionLoader.hasExtension("xxx"));

        assertFalse(extensionLoader.hasExtension("wrapper1"));

        try {
            extensionLoader.hasExtension(null);
            fail();
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage(), containsString("Extension name == null"));
        }
    }

    @Test
    public void test_getSupportedExtensions() throws Exception {
        Set<String> extensions = ExtensionLoader.getExtensionLoader(SimpleExt.class).getSupportedExtensions();

        Set<String> expected = new HashSet<String>();
        expected.add("impl1");
        expected.add("impl2");
        expected.add("impl3");

        assertEquals(expected, extensions);
    }

    @Test
    public void test_getSupportedExtensions_wrapperIsNotExt() throws Exception {
        Set<String> extensions = ExtensionLoader.getExtensionLoader(WrappedExt.class).getSupportedExtensions();

        Set<String> expected = new HashSet<String>();
        expected.add("impl1");
        expected.add("impl2");

        assertEquals(expected, extensions);
    }

    @Test
    public void test_getExtensionAttribute_all() throws Exception {
        ExtensionLoader<WithAttributeExt> extensionLoader = ExtensionLoader.getExtensionLoader(WithAttributeExt.class);

        Map<String, Map<String, String>> extensionAttribute = extensionLoader.getExtensionAttribute();

        Map<String, Map<String, String>> expected = new HashMap<String, Map<String, String>>();
        expected.put("impl1", Utils.kv2Map("k1", "v1", "k2", "", "k3", "v3", "k4", "", "k5", "v5"));
        expected.put("impl2", new HashMap<String, String>());
        expected.put("impl3", Utils.kv2Map("key1", "value1", "key2", "value2", "key3", ""));

        assertEquals(expected, extensionAttribute);
    }

    @Test
    public void test_getExtensionAttribute_one() throws Exception {
        ExtensionLoader<WithAttributeExt> extensionLoader = ExtensionLoader.getExtensionLoader(WithAttributeExt.class);
        Map<String, String> extensionAttribute = extensionLoader.getExtensionAttribute("impl1");
        assertEquals(Utils.kv2Map("k1", "v1", "k2", "", "k3", "v3", "k4", "", "k5", "v5"), extensionAttribute);
    }

    @Test
    public void test_getExtensionAttribute_oneNotExisted() throws Exception {
        ExtensionLoader<WithAttributeExt> extensionLoader = ExtensionLoader.getExtensionLoader(WithAttributeExt.class);

        try {
            Map<String, String> extensionAttribute = extensionLoader.getExtensionAttribute("NotExisted");
            fail();
        } catch (IllegalStateException expected) {
            assertThat(expected.getMessage(), containsString("No such extension com.winit.cloudlink.common.extension.ext4.WithAttributeExt by name NotExisted"));
        }
    }

    @Test
    public void test_getAdaptiveInstance_InjectNotExtFail() throws Exception {
        InjectExt ext = ExtensionLoader.getExtensionLoader(InjectExt.class).getExtension("impl2");

        Ext6Impl2 impl = (Ext6Impl2) ext;
        assertNull(impl.getList());
    }

    @Test
    public void test_InitError() throws Exception {
        ExtensionLoader<InitErrorExt> loader = ExtensionLoader.getExtensionLoader(InitErrorExt.class);

        loader.getExtension("ok");

        try {
            loader.getExtension("error");
            fail();
        } catch (IllegalStateException expected) {
            assertThat(expected.getMessage(), containsString("Failed to load config line(error=com.winit.cloudlink.common.extension.ext7.impl.Ext7InitErrorImpl)"));
            assertThat(expected.getMessage(), containsString("com.winit.cloudlink.common.extension.ext7.InitErrorExt) for extension(com.winit.cloudlink.common.extension.ext7.InitErrorExt)"));
            assertThat(expected.getCause().getCause(), instanceOf(ExceptionInInitializerError.class));
        }
    }

    @Test
    public void test_InvalidExtName() throws Exception {
        try {
            ExtensionLoader.getExtensionLoader(InvalidNameExt.class);
            fail();
        } catch (IllegalStateException expected) {
            assertThat(expected.getMessage(),
                    containsString("default name(invalid-name&) of extension com.winit.cloudlink.common.extension.ext8.InvalidNameExt is invalid"));
        }
    }

    // 新增测试 a-Z 中的 ] [ 等非法符号，见 Issue #56
    @Test
    public void test_InvalidExtName2() throws Exception {
        try {
            ExtensionLoader.getExtensionLoader(InvalidNameExt2.class);
            fail();
        } catch (IllegalStateException expected) {
            assertThat(expected.getMessage(),
                    containsString("default name(invalidName]) of extension com.winit.cloudlink.common.extension.ext8.InvalidNameExt2 is invalid"));
        }
    }

    @Test
    public void test_ImplNoDefaultConstructor() throws Exception {
        ExtensionLoader<ImplNoDefaultConstructorExt> extensionLoader = ExtensionLoader.getExtensionLoader(ImplNoDefaultConstructorExt.class);
        assertEquals(new HashSet<String>(Arrays.asList("impl2")), extensionLoader.getSupportedExtensions());
    }

    @Test
    public void test_parseExtAttribute() throws Exception {
        assertEquals(Utils.kv2Map("k1", "v1", "k2", "v2"), ExtensionLoader.parseExtAttribute("k1=v1,k2=v2"));
        assertEquals(Utils.kv2Map("k1", "v1", "k2", "v2", "k3", ""), ExtensionLoader.parseExtAttribute("  k1   =v1,   k2=    v2  , k3    "));
        assertEquals(Utils.kv2Map("k1", ""), ExtensionLoader.parseExtAttribute("k1"));
    }
}

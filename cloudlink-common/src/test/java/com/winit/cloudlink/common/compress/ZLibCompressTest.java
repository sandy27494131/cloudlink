package com.winit.cloudlink.common.compress;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class ZLibCompressTest {

    public static final String CHARSET  = "UTF-8";

    public static Compress     compress = new ZLibCompress();

    @Test
    public void testCompress() {
        String text = "测试字符串压缩";
        try {
            byte[] data = compress.compress(text.getBytes(CHARSET));

            assertNotNull(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            assertNotNull(e);
        } catch (Exception e) {
            e.printStackTrace();
            assertNotNull(e);
        }
    }

    @Test
    public void testDecompress() {
        String text = "测试字符串压缩";
        try {
            byte[] data = compress.compress(text.getBytes(CHARSET));

            assertNotNull(data);

            byte[] newData = compress.decompress(data);
            assertNotNull(newData);
            String newText = new String(newData, CHARSET);
            assertEquals(text, newText);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            assertNotNull(e);
        } catch (Exception e) {
            e.printStackTrace();
            assertNotNull(e);
        }
    }

}

import com.winit.cloudlink.message.importer.service.support.MessageFileFilter;
import com.winit.cloudlink.message.importer.utils.DateTimes;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Date;

/**
 * Created by stvli on 2017/3/31.
 */
public class CloudlinkMessageFileFilterTest {
    private String basePath = "E:\\workspace\\winit2.0\\frameworks\\trunk\\cloudlink\\cloudlink-message-importer\\src\\test\\resources\\AUR\\CNR\\";

    @Test
    public void test1() {
        File file = new File(basePath);
        Date start = DateTimes.parse("2017-03-31 09:31:51");
        Assert.assertTrue(file.listFiles(new MessageFileFilter(start)).length == 6);
        start = DateTimes.parse("2017-03-31 10:31:51");
        Assert.assertTrue(file.listFiles(new MessageFileFilter(start)).length == 5);
        start = DateTimes.parse("2017-03-31 14:00:00");
        Assert.assertTrue(file.listFiles(new MessageFileFilter(start)).length == 1);
        start = DateTimes.parse("2017-03-30 23:59:59");
        Assert.assertTrue(file.listFiles(new MessageFileFilter(start)).length == 6);
    }
}

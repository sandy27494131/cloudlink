import org.apache.zookeeper.common.IOUtils;

import java.io.*;

/**
 * Created by stvli on 2017/3/31.
 */
public class SampleCloudlinkCommandDataBuilder {

    private String basePath = "E:\\workspace\\winit2.0\\frameworks\\trunk\\cloudlink\\cloudlink-message-importer\\src\\test\\resources\\AUR\\CNR\\";
    private File sourceFile = new File(basePath + "temp.log");
    private File destFile = new File(basePath + "cloudlink-message_2017-03-31-10.log");

    public void startBuilder() {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(sourceFile));
            writer = new BufferedWriter(new FileWriter(destFile, false));

        } catch (IOException e) {
            System.out.println(e);
        } finally {
            IOUtils.closeStream(reader);
            IOUtils.closeStream(writer);
        }

    }

    public static final void main(String[] args) {
        SampleCloudlinkCommandDataBuilder builder = new SampleCloudlinkCommandDataBuilder();
    }
}

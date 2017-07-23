package com.winit.cloudlink.message.importer.service;

import com.google.common.collect.Lists;
import com.winit.cloudlink.message.Message;
import com.winit.cloudlink.message.importer.service.support.MessageFileFilter;
import com.winit.cloudlink.message.importer.service.support.MessageSender;
import com.winit.cloudlink.message.importer.service.support.MessageHelper;
import com.winit.cloudlink.message.importer.utils.DateTimes;
import org.apache.zookeeper.common.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

import static com.winit.cloudlink.message.importer.utils.DateTimes.diffSeconds;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by stvli on 2017/3/31.
 */
@Service
public class MessageImportService {
    private static Logger logger = LoggerFactory.getLogger(MessageImportService.class);

    @Value("${spring.cloudlink.message.import.basepath:/opt/cloudlink-message/AUR/CNR}")
    private String basePath;

    @Autowired
    private MessageSender cloudlinkMessageSender;

    @PostConstruct
    public void init() {
    }

    public int startImport(Date start) {
        Date beginTime = new Date();
        logger.info("开始导入目录{}下的云链消息，起始日期为{}...", basePath, DateTimes.format(start));
        List<File> files = getAvaibleFiles(new File(basePath), start);
        if (null == files || files.size() == 0) {
            logger.info("目录{}下无符合条件的云链消息文件", basePath);
            return 0;
        }
        logger.info("目录{}下共有{}个符合条件的云链消息文件", basePath, files.size());
        int count = 0;
        for (File file : files) {
            count += importMessageFromFile(file, start);
        }
        Date endTime = new Date();
        logger.info("导入完成，总共导入{}条消息，耗时{}秒。", count, diffSeconds(beginTime, endTime));
        return count;
    }

    private int importMessageFromFile(File file, Date start) {
        logger.info("正在导入文件{}里的云链消息", file.getName());
        BufferedReader reader = null;
        int count = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            do {
                line = reader.readLine();
                if (isNotBlank(line)) {
                    Message message = MessageHelper.buildMessage(line);
                    if (!avablieMessage(message, start)) {
                        continue;
                    }
                    publishMessage(line);
                    count++;
                }
            } while (line != null);
        } catch (IOException e) {
            logger.error("读取文件时发生错误。", e);
        } catch (Exception e) {
            logger.error("导入消息时发生错误。", e);
        } finally {
            IOUtils.closeStream(reader);
        }
        logger.info("文件{}里云链消息已导入，共导入{}条消息。", file.getName(), count);
        return count;
    }

    private boolean avablieMessage(Message message, Date start) {
        Long messageTime = message.getHeaders().getTimestamp();
        return null == messageTime ? false : messageTime >= start.getTime();
    }

    public List<File> getAvaibleFiles(File path, Date start) {
        List<File> result = Lists.newArrayList();
        File[] files = path.listFiles(new MessageFileFilter(start));
        if (null == files || files.length == 0) {
            return result;
        }
        result = Arrays.asList(files);
        //按修改时间倒序
        Collections.sort(result, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;
            }
        });
        return result;
    }

    private void publishMessage(String messageString) {
        cloudlinkMessageSender.sendMessage(messageString);
    }
}

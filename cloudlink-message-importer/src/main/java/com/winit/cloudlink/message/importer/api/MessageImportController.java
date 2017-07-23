package com.winit.cloudlink.message.importer.api;

import com.winit.cloudlink.message.importer.service.MessageImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by stvli on 2017/3/31.
 */
@RestController
@Api(value = "importer", tags = {"importer"}, description = "消息导入API")
@RequestMapping("/api/v1/importer")
public class MessageImportController {
    private static Logger logger = LoggerFactory.getLogger(MessageImportController.class);

    @Autowired
    private MessageImportService messageImportService;


    @RequestMapping(value = "/import", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "导入消息", notes = "根据SendLog对象创建新的发送日志，返回此发送的ID。")
    public ResponseEntity<String> importMessage(@ApiParam(value = "起始时间，时间格式yyyy-MM-dd HH:mm:ss", required = true, defaultValue = "2017-04-06 00:00:00") @RequestParam(value = "start", required = true) Date start,
                                                UriComponentsBuilder ucBuilder, HttpServletRequest request, HttpServletResponse response) {
        int count = messageImportService.startImport(start);
        return new ResponseEntity<String>(String.valueOf(count), HttpStatus.OK);
    }
}

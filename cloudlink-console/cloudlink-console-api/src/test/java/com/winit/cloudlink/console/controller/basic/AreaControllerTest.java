package com.winit.cloudlink.console.controller.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winit.cloudlink.console.SpringControllerTestBase;
import com.winit.cloudlink.console.bean.ErrorCode;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.storage.api.vo.AreaVo;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

public class AreaControllerTest extends SpringControllerTestBase {

    @Resource
    private ApplicationContext applicationContext;

    @Test
    public void testAll() {
        try {
            String code = "TEST_AREA";
            ObjectMapper mapper = new ObjectMapper();
            ResponseBean result = new ResponseBean();

            // 测试删除,清理测试数据
            String response = this.mockDelete("/api/area/" + code, null);
            result = mapper.readValue(response, result.getClass());
            assertEquals(0, result.getErrorCode());

            // 测试新增
            AreaVo areaVo = new AreaVo();
            areaVo.setCode(code);
            areaVo.setName("TEST_AREA");
            areaVo.setMqWanAddr("amqp://guest:guest@172.16.3.164:5672");
            areaVo.setMqMgmtAddr("http://guest:guest@172.16.3.164:15672");
            areaVo.setRemark("TEST_AREA");

            String json = mapper.writeValueAsString(areaVo);
            response = this.mockPost("/api/area/" + code, json);
            System.out.println(response);
            result = new ResponseBean();
            result.setData("");
            result = mapper.readValue(response, result.getClass());

            assertEquals(ErrorCode.SUCCESS.getErrorCode(), result.getErrorCode());

            // 测试编码重复新增
            json = mapper.writeValueAsString(areaVo);
            response = this.mockPost("/api/area/" + code, json);
            System.out.println(response);
            result = mapper.readValue(response, result.getClass());

            assertEquals(ErrorCode.AREA_EXISTS.getErrorCode(), result.getErrorCode());

            // 测试参数错误
            areaVo.setName(null);
            json = mapper.writeValueAsString(areaVo);
            response = this.mockPost("/api/area/" + code, json);
            System.out.println(response);
            result = mapper.readValue(response, result.getClass());

            assertEquals(ErrorCode.PARAM_ERROR.getErrorCode(), result.getErrorCode());

            // 根据编号查询数据中心
            response = mockGet("/api/area/" + code, null);
            result.setData(new AreaVo());
            result = mapper.readValue(response, result.getClass());

            assertEquals(ErrorCode.SUCCESS.getErrorCode(), result.getErrorCode());

            // 测试修改功能
            areaVo.setName("TEST_AREA1");
            areaVo.setMqWanAddr("amqp://guest:guest@172.16.3.164:5672");
            areaVo.setMqMgmtAddr("http://guest:guest@172.16.3.164:15672");
            areaVo.setRemark("TEST_AREA1");

            json = mapper.writeValueAsString(areaVo);
            response = this.mockPut("/api/area/" + code, json);
            result = new ResponseBean();
            result = mapper.readValue(response, result.getClass());
            assertEquals(ErrorCode.SUCCESS.getErrorCode(), result.getErrorCode());

            // 测试修改参数错误
            areaVo.setName(null);
            json = mapper.writeValueAsString(areaVo);
            response = this.mockPut("/api/area/" + code, json);
            result = new ResponseBean();
            result = mapper.readValue(response, result.getClass());
            assertEquals(ErrorCode.PARAM_ERROR.getErrorCode(), result.getErrorCode());

            // 查询所有数据中心
            response = mockGet("/api/areas", "");
            result.setData(new ArrayList<AreaVo>());
            result = mapper.readValue(response, result.getClass());

            assertEquals(0, result.getErrorCode());
            assertNotNull(result.getData());
            assertTrue(((ArrayList) result.getData()).size() > 0);

            // 测试删除
            response = this.mockDelete("/api/area/TEST_AREA", null);
            result = mapper.readValue(response, result.getClass());
            assertEquals(ErrorCode.SUCCESS.getErrorCode(), result.getErrorCode());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}

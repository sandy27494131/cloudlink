package com.winit.cloudlink.console;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.google.gson.annotations.JsonAdapter;
import com.winit.cloudlink.console.bean.ResponseBean;
import com.winit.cloudlink.console.utils.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp")
@ContextConfiguration({ "classpath*:applicationContext.xml", "classpath*:spring-servlet.xml" })
@ActiveProfiles("test")
public class SpringControllerTestBase {

    private static final Locale   locale = Locale.SIMPLIFIED_CHINESE;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc               mockMvc;

    private String                token  = "";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        ResponseBean response = new ResponseBean();
        try {
            String json = mockPost("/api/login", "{\"username\":\"admin\",\"password\":\"winit2015\"}");
            response = JSON.parseObject(json, ResponseBean.class);
            if (null != response.getData()) {
                token = response.getData().toString();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WebApplicationContext getWac() {
        return wac;
    }

    public MockMvc getMockMvc() {
        return mockMvc;
    }

    public String mockPost(String uri, String json) throws UnsupportedEncodingException, Exception {
        return mockMvc.perform(post(uri).characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header(Constants.KEY_HTTP_HEADER_AUTH, token)
            .content(null != json ? json.getBytes() : null)
            .locale(locale))
            .andReturn()
            .getResponse()
            .getContentAsString();
    }

    public String mockGet(String uri, String json) throws UnsupportedEncodingException, Exception {
        return mockMvc.perform(get(uri, "json").characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header(Constants.KEY_HTTP_HEADER_AUTH, token)
            .content(null != json ? json.getBytes() : null)
            .locale(locale))
            .andReturn()
            .getResponse()
            .getContentAsString();
    }

    public String mockPut(String uri, String json) throws UnsupportedEncodingException, Exception {
        return mockMvc.perform(put(uri, "json").characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header(Constants.KEY_HTTP_HEADER_AUTH, token)
            .content(null != json ? json.getBytes() : null)
            .locale(locale))
            .andReturn()
            .getResponse()
            .getContentAsString();
    }

    public String mockDelete(String uri, String json) throws UnsupportedEncodingException, Exception {
        return mockMvc.perform(delete(uri, "json").characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .header(Constants.KEY_HTTP_HEADER_AUTH, token)
            .content(null != json ? json.getBytes() : null)
            .locale(locale))
            .andReturn()
            .getResponse()
            .getContentAsString();
    }

}

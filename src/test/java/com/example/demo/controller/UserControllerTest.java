package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.dto.UserRoleDTO;
import com.example.demo.entity.po.UserPO;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserController userController;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Value("${test.token}")
    private String token;

    @BeforeEach
    void setUp(){
        //自己指定要测试的Controller
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @Order(1)
    void listAll() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/listAll")
                        // .content(json.getBytes()) //传json参数
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("token",token)
                );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    @Order(2)
    void list() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/list")
                        // .content(json.getBytes()) //传json参数
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("token",token)
                );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    @Order(3)
    void add() throws Exception {
        UserPO userPO = new UserPO();
        userPO.setUsername("ddddd");
        userPO.setPassword(passwordEncoder.encode("123456"));
        userPO.setNickName("ddddd");
        userPO.setRealName("ddddd");
        userPO.setEmail("2626262626@qq.com");
        userPO.setPhone("13888888888");
        userPO.setGender(1);
        String json = JSON.toJSONString(userPO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/add")
                        .content(json.getBytes()) //传json参数
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("token",token)
                );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    @Order(4)
    void update() throws Exception {
        UserPO userPO = userService.getUserByUsername("ddddd");
        userPO.setNickName("bbbbb");

        String json = JSON.toJSONString(userPO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .put("/user/update")
                        .content(json.getBytes()) //传json参数
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("token",token)
                );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    @Order(10)
    void delete() throws Exception {
        UserPO userPO = userService.getUserByUsername("ddddd");
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/user/delete/" + userPO.getId())
                        // .content(json.getBytes()) //传json参数
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("token",token)
                );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    @Order(5)
    void getRoleListForAssign() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/getRoleListForAssign")
                        // .content(json.getBytes()) //传json参数
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("token",token)
                );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    @Order(6)
    void getRoleListByUserId() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/getRoleListByUserId/1")
                        // .content(json.getBytes()) //传json参数
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("token",token)
                );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    @Order(7)
    void saveUserRole() throws Exception {
        Long id = userService.getUserByUsername("ddddd").getId();

        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserId(id);
        userRoleDTO.setRoleIds(new ArrayList(){{add(3L);}});

        String json = JSON.toJSONString(userRoleDTO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/saveUserRole")
                        .content(json.getBytes()) //传json参数
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("token",token)
                );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    @Order(8)
    void register() throws Exception {
        UserPO userPO = new UserPO();
        userPO.setUsername("wwwww");
        userPO.setPassword(passwordEncoder.encode("123456"));
        userPO.setNickName("www");
        userPO.setRealName("www");
        userPO.setEmail("2626262626@qq.com");
        userPO.setPhone("13888888888");
        userPO.setGender(1);
        String json = JSON.toJSONString(userPO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/register")
                        .content(json.getBytes()) //传json参数
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("token",token)
                );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    @Order(9)
    void updatePassword() throws Exception {
        UserPO userPO = userService.getUserByUsername("ddddd");
        userPO.setPassword(passwordEncoder.encode("123123"));

        String json = JSON.toJSONString(userPO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .put("/user/updatePassword")
                        .content(json.getBytes()) //传json参数
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("token",token)
                );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }
}
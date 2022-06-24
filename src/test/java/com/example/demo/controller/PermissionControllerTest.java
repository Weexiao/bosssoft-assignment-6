package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.po.PermissionPO;
import com.example.demo.service.PermissionService;
import com.example.demo.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class PermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PermissionController permissionController;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Value("${test.token}")
    private String token;

    @BeforeEach
    void setUp(){
        //自己指定要测试的Controller
        mockMvc = MockMvcBuilders.standaloneSetup(permissionController).build();
    }
    @Test
    @Order(1)
    void getMenuList() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/permission/list")
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
    void getParentList() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/permission/parent/list")
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
    void getMenuById() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/permission/1")
                // .content(json.getBytes()) //传json参数
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("token",token)
        );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    @Order(4)
    void add() throws Exception {
        PermissionPO permissionPO = new PermissionPO();
        permissionPO.setLabel("nonono");
        permissionPO.setParentId(0L);
        permissionPO.setParentName("顶级菜单");
        permissionPO.setCode("wuhu");
        permissionPO.setPath("/wuhu");
        permissionPO.setName("wuhu");
        permissionPO.setUrl("/wuhu/index");
        permissionPO.setType(0);
        String json = JSON.toJSONString(permissionPO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/permission/add")
                .content(json.getBytes()) //传json参数
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("token",token)
        );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    @Order(5)
    void update() throws Exception {
        PermissionPO permissionPO = permissionService.getPermissionByUrl("/wuhu/index");
        permissionPO.setLabel("yesyesyes");
        String json = JSON.toJSONString(permissionPO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/permission/update")
                .content(json.getBytes()) //传json参数
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("token",token)
        );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    @Order(7)
    void delete() throws Exception {
        Long id = permissionService.getPermissionByUrl("/wuhu/index").getId();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .delete("/permission/delete/" + id)
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
    void check() throws Exception {
        Long id = permissionService.getPermissionByUrl("/wuhu/index").getId();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/permission/check/" + id.toString())
                // .content(json.getBytes()) //传json参数
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("token",token)
        );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }
}
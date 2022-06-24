package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.dto.RolePermissionDTO;
import com.example.demo.entity.po.RolePO;
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

import javax.annotation.Resource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoleController roleController;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Value("${test.token}")
    private String token;

    @BeforeEach
    void setUp(){
        //自己指定要测试的Controller
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    @Order(1)
    void findRoleListByUserId() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/role/list")
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
    void add() throws Exception {
        RolePO rolePO = new RolePO();
        rolePO.setRoleCode("ROLE_TEST");
        rolePO.setRoleName("测试用户");
        rolePO.setCreateUser(1L);
        String json = JSON.toJSONString(rolePO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/role/add")
                .content(json.getBytes()) //传json参数
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("token",token)
        );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    @Order(3)
    void update() throws Exception {
        RolePO rolePO = roleService.getRoleByCode("ROLE_TEST");
        rolePO.setRoleCode("ROLE_TEST_UPDATE");
        rolePO.setRoleName("测试用户");
        rolePO.setCreateUser(1L);
        String json = JSON.toJSONString(rolePO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/role/update")
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
        Long id = roleService.getRoleByCode("ROLE_TEST_UPDATE").getId();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .delete("/role/delete/" + id)
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
    void getAssignPermissionTree() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/role/getAssignPermissionTree")
                //.content(json.toJSONString().getBytes()) //传json参数
                .param("userId", "1")
                .param("roleId", "1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("token",token)
        );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }

    @Test
    @Order(5)
    void saveRolePermission() throws Exception {
        RolePO rolePO = roleService.getRoleByCode("ROLE_TEST_UPDATE");
        RolePermissionDTO rolePermissionDTO = new RolePermissionDTO();
        rolePermissionDTO.setRoleId(rolePO.getId());
        rolePermissionDTO.setPermissionIds(new ArrayList<Long>(){{
            add(18L);add(19L);
        }});
        String json = JSON.toJSONString(rolePermissionDTO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/role/saveRolePermission")
                .content(json.getBytes()) //传json参数
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
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/role/check/1")
                // .content(json.getBytes()) //传json参数
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("token",token)
        );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }
}
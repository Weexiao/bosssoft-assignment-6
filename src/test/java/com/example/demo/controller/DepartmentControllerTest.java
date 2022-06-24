package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.po.DepartmentPO;
import com.example.demo.service.DepartmentService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentController departmentController;

    @Value("${test.token}")
    private String token;

    @BeforeEach
    void setUp(){
        //自己指定要测试的Controller
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }


    @Test
    @Order(1)
    void findDepartmentList() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/department/list")
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
    void findParentDepartment() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/department/parent/list")
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
    void addDepartment() throws Exception {
        DepartmentPO departmentPO = new DepartmentPO();
        departmentPO.setDepartmentName("Google");
        departmentPO.setPhone("13888888888");
        departmentPO.setAddress("爱尔兰都柏林市");
        departmentPO.setPid(0L);
        departmentPO.setParentName("顶级部门");
        departmentPO.setOrderNum(0);
        String json = JSON.toJSONString(departmentPO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/department/add")
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
    void updateDepartment() throws Exception {
        DepartmentPO departmentPO = departmentService.getDepartmentByName("Google");
        departmentPO.setPhone("13899999999");

        String json = JSON.toJSONString(departmentPO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/department/update")
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
    void hasChildOfDepartment() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/department/check/1")
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
    void deleteDepartment() throws Exception {
        Long id = departmentService.getDepartmentByName("Google").getId();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .delete("/department/delete/" + id)
                // .content(json.getBytes()) //传json参数
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("token",token)
        );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    }
}
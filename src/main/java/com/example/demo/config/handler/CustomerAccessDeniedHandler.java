package com.example.demo.config.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.common.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 认证用户访问无权限资源时的处理类
 */
@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        // 设置响应的内容编码格式
        httpServletResponse.setContentType("application/json;charset=utf-8");
        // 获取输出流
        ServletOutputStream out = httpServletResponse.getOutputStream();
        // 消除循环引用
        String result = JSON.toJSONString(
                Result.error().code(700).message("您没有权限访问该资源"),
                SerializerFeature.DisableCircularReferenceDetect
        );

        // 将Json格式的字符串写入输出流
        out.write(result.getBytes(StandardCharsets.UTF_8));
        out.flush();
        // 关闭输出流
        out.close();
    }
}


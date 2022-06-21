package com.example.demo.config.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 匿名用户访问资源处理器
 */
@Slf4j
@Component
public class AnonymousAuthenticationHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AuthenticationException e)
            throws IOException, ServletException {
        log.debug("匿名用户访问资源处理器配置成功");
        // 设置响应的内容编码格式
        httpServletResponse.setContentType("application/json;charset=utf-8");
        // 获取输出流
        ServletOutputStream out = httpServletResponse.getOutputStream();
        // 消除循环引用
        String result = JSON.toJSONString(
                Result.error().code(600).message("匿名用户无权访问"),
                SerializerFeature.DisableCircularReferenceDetect);

        // 将Json格式的字符串写入输出流
        out.write(result.getBytes(StandardCharsets.UTF_8));
        out.flush();
        // 关闭输出流
        out.close();
    }
}

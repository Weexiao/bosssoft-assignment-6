package com.example.demo.config.handler;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.Result;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 登录认证失败处理器类
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        // 设置响应的内容编码格式
        httpServletResponse.setContentType("application/json;charset=utf-8");
        // 获取输出流
        ServletOutputStream out = httpServletResponse.getOutputStream();
        String message = null; // 设置错误信息
        int code = 500; // 设置错误码
        // 判断异常类型
        if(exception instanceof AccountExpiredException){
            message = "账户过期,登录失败！";
        }else if(exception instanceof BadCredentialsException){
            message = "用户名或密码错误,登录失败！";
        }else if(exception instanceof CredentialsExpiredException){
            message = "密码过期,登录失败！";
        }else if(exception instanceof DisabledException){
            message = "账户被禁用,登录失败！";
        }else if(exception instanceof LockedException){
            message = "账户被锁,登录失败！";
        }else if(exception instanceof InternalAuthenticationServiceException){
            message = "账户不存在,登录失败！";
        }else{
            message = "登录失败！";
        }

        // 将错误信息转换为Json格式
        String result = JSON.toJSONString(Result.error().code(code).message(message));
        // 将Json格式的字符串写入输出流
        out.write(result.getBytes(StandardCharsets.UTF_8));
        out.flush();
        // 关闭输出流
        out.close();
    }
}

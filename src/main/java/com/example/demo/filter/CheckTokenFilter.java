package com.example.demo.filter;

import com.example.demo.config.handler.LoginFailureHandler;
import com.example.demo.config.redis.RedisService;
import com.example.demo.config.security.CustomerUserDetailsService;
import com.example.demo.exception.CustomAuthenticationException;
import com.example.demo.utils.JwtUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token验证过滤器
 */
@Data
@Component
public class CheckTokenFilter extends OncePerRequestFilter {

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private CustomerUserDetailsService userDetailsService;

    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Resource
    private RedisService redisService;

    // 获取登录请求地址
    @Value("${request.login.url}")
    private String loginUrl;

    // 获取注册请求地址
    @Value("${request.register.url}")
    private String registerUrl;

    // 获取忘记密码请求地址
    @Value("${request.forget.url}")
    private String forgetUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 获取当前请求地址
            String url = httpServletRequest.getRequestURI();
            // 如果不是登录请求，则要验证token
            if (!(url.equals(loginUrl) || url.equals(registerUrl) || url.equals(forgetUrl))) {
                // 验证token
                this.validateToken(httpServletRequest);
            }
        } catch (AuthenticationException e) {
            // 认证失败，跳转到登录页面
            loginFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
        }
        // 登录请求不需要过滤
        doFilter(httpServletRequest, httpServletResponse, filterChain);
    }

    /**
     * 验证token
     * @param httpServletRequest
     */
    private void validateToken(HttpServletRequest httpServletRequest) {
        // 获取token
        String token = httpServletRequest.getHeader("token");
        // 如果请求头部没有获取到token，则从请求参数中获取
        if (ObjectUtils.isEmpty(token)) {
            token = httpServletRequest.getParameter("token");
        }
        // 如果请求头部和请求参数都没有获取到token，则抛出异常
        if (ObjectUtils.isEmpty(token)) {
            throw new CustomAuthenticationException("token不存在");
        }

        // 判断redis中是否存在该token
        String tokenKey = "token_" + token;
        String redisToken = redisService.get(tokenKey);
        // 如果redis中不存在该token，证明该token失效
        if (ObjectUtils.isEmpty(redisToken)) {
            throw new CustomAuthenticationException("token失效");
        }
        // 如果与redis中token不一致，则验证失败
        if (!redisToken.equals(token)) {
            throw new CustomAuthenticationException("token验证失败");
        }
        // 如果存在token，则从token中取出用户名
        String username = jwtUtils.getUsernameFromToken(token);
        // 判断该用户名是否为空
        if (ObjectUtils.isEmpty(username)) {
            throw new CustomAuthenticationException("token解析失败");
        }

        // 获取用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 判断用户信息是否为空
        if (ObjectUtils.isEmpty(userDetails)) {
            throw new CustomAuthenticationException("用户不存在");
        }

        // 创建身份验证对象
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        // 设置到Spring Security上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

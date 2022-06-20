package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.config.redis.RedisService;
import com.example.demo.entity.vo.TokenVO;
import com.example.demo.utils.JwtUtils;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 刷新Token
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Resource
    private RedisService redisService;
    @Resource
    private JwtUtils jwtUtils;

    /**
     * 刷新Token
     * @param request
     * @return
     */
    @PostMapping("/refreshToken")
    public Result refreshToken(HttpServletRequest request) {
        // 获取token
        String token = request.getHeader("token");
        // 如果header中不存在token，则从参数中获取
        if (ObjectUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        // 从Spring Security上下文中获取用户信息
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        // 获取身份信息
        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();
        // 重新生成token
        String reToken = "";
        // 验证原来的Token是否合法
        if (jwtUtils.validateToken(token, userDetails)) {
            // 如果token过期，则刷新token
            reToken = jwtUtils.refreshToken(token);
        }

        // 获取本次token的到期时间，交给前端做判断
        Long expireTime = Jwts.parser()
                .setSigningKey(jwtUtils.getSecret())
                .parseClaimsJws(reToken.replace("jwt_", ""))
                .getBody()
                .getExpiration().getTime();

        // 清除原来的Token信息
        String oldToken = "token_" + token;
        redisService.del(oldToken);
        // 将新的Token存入
        String newTokenKey = "token_" + reToken;
        redisService.set(newTokenKey, reToken, jwtUtils.getExpiration() / 1000);

        // 创建TokenVO对象，返回给前端
        TokenVO tokenVO = new TokenVO(expireTime, reToken);
        // 返回结果
        return Result.ok(tokenVO).message("刷新Token成功");
    }
}

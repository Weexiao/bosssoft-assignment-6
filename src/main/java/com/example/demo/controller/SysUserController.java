package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.config.redis.RedisService;
import com.example.demo.entity.dto.UserDTO;
import com.example.demo.entity.po.PermissionPO;
import com.example.demo.entity.po.UserPO;
import com.example.demo.entity.vo.RouterVO;
import com.example.demo.entity.vo.TokenVO;
import com.example.demo.entity.vo.UserVO;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.MenuTreeUtils;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 获取当前登录用户的信息
     * @return
     */
    @GetMapping("/getInfo")
    public Result getInfo() {
        // 从Spring security上下文中获取当前登录用户的信息
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        // 判断authentication是否为空
        if (ObjectUtils.isEmpty(authentication)) {
            return Result.error().message("用户信息查询失败");
        }
        // 获取身份信息
        UserPO userPO = (UserPO) authentication.getPrincipal();
        // 用户权限集合
        List<PermissionPO> permissionPOList = userPO.getPermissionList();
        // 获取用户权限编码字段
        Object[] roles = permissionPOList.stream()
                .filter(item -> item != null)
                .map(item -> item.getCode()).toArray();
        // 创建用户信息对象
        UserDTO userDTO = new UserDTO(userPO.getId(), userPO.getNickName(), userPO.getAvatar(),
                null, roles);

        // 返回结果
        return Result.ok(userDTO).message("用户信息查询成功");
    }

    /**
     * 获取菜单数据
     * @return
     */
    @GetMapping("/getMenuList")
    public Result getMenuList(){
        // 从Spring security上下文中获取当前登录用户的信息
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        // 判断authentication是否为空
        if (ObjectUtils.isEmpty(authentication)) {
            return Result.error().message("用户信息查询失败");
        }
        // 获取身份信息
        UserPO userPO = (UserPO) authentication.getPrincipal();
        // 用户权限集合
        List<PermissionPO> permissionPOList = userPO.getPermissionList();

        // 筛选目录和菜单
        List<PermissionPO> collect = permissionPOList.stream()
                .filter(item -> item != null && item.getType() != 2)  // 忽略按钮
                .collect(Collectors.toList());

        // 生成路由数据
        List<RouterVO> routerVOList = MenuTreeUtils.makeRouter(collect, 0L);
        // 返回数据
        return Result.ok(routerVOList).message("菜单查询成功");
    }
}

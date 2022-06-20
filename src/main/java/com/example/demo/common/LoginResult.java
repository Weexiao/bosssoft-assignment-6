package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult {
    // 用户编号
    private Long userId;
    // 状态码
    private int status;
    // token令牌
    private String token;
    // token过期时间
    private Long expireTime;
}

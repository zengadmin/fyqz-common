/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TokenInterceptor.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.fyqz.interceptor;


import com.fyqz.constants.Constants;
import com.fyqz.dto.LoginUserDto;
import com.fyqz.util.DataUtil;
import com.fyqz.util.JwtUtils;
import com.fyqz.util.ThreadLocalMap;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@Slf4j
public class TokenInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private JwtUtils jwtUtils;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String token =request.getHeader("TOKEN");
		if(DataUtil.isNotEmpty(token)){
			Claims claims = jwtUtils.getClaimByToken(token);
			if(claims != null && !jwtUtils.isTokenExpired(claims.getExpiration())){
				LoginUserDto dto=new LoginUserDto();
				dto.setUserId(claims.getSubject());
				ThreadLocalMap.put(Constants.TOKEN_USER, dto);
			}
		}
		return true;
	}
}
  
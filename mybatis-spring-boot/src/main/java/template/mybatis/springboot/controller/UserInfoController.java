/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package template.mybatis.springboot.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import template.mybatis.springboot.authorization.annotation.Authorization;
import template.mybatis.springboot.authorization.manager.impl.TokenManager;
import template.mybatis.springboot.authorization.model.TokenModel;
import template.mybatis.springboot.model.ResultModel;
import template.mybatis.springboot.model.UserInfo;
import template.mybatis.springboot.service.UserInfoService;

/**
 * @author lwq
 */
@RestController
@RequestMapping("/users")
@Api("用户授权操作")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
	private TokenManager tokenManager;

    
    /**
     * 登录以及授权
     */
    @ResponseBody
    @PostMapping("/login")
    @ApiOperation("登录以及授权")
    public ResponseEntity<ResultModel> login(@RequestBody Map<String, String> request ) {
    	UserInfo login = userInfoService.login(request.get("username"), request.get("password"));
    	if(login!=null){
    		TokenModel createToken = tokenManager.createToken(request.get("username"));
    		return ResultModel.success(createToken);
    	}else{
    		return ResultModel.fail(403, "用户名或密码错误");
    		
    	}
    }
    
    /**
     * 
     * @param authorization token 取消授权
     * @return
     */
    @PostMapping("/loginOut")
    @Authorization
    @ApiOperation("取消授权")
    public ResponseEntity<ResultModel> loginOut(@RequestHeader String authorization ) {
    	tokenManager.deleteToken(authorization);
    	 return ResultModel.success();
    		
    }
}

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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;

import template.mybatis.springboot.authorization.annotation.Authorization;
import template.mybatis.springboot.authorization.manager.impl.TokenManager;
import template.mybatis.springboot.authorization.model.TokenModel;
import template.mybatis.springboot.model.ResultModel;
import template.mybatis.springboot.model.ResultStatus;
import template.mybatis.springboot.model.UserInfo;
import template.mybatis.springboot.service.UserInfoService;

/**
 * @author liuzh
 * @since 2015-12-19 11:10
 */
@RestController
@RequestMapping("/users")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
	private TokenManager tokenManager;



    @RequestMapping
    @Authorization
    public PageInfo<UserInfo> getAll(UserInfo userInfo) {
        List<UserInfo> userInfoList = userInfoService.getAll(userInfo);
        return new PageInfo<UserInfo>(userInfoList);
    }

    @RequestMapping(value = "/add")
    public UserInfo add() {
        return new UserInfo();
    }

    @RequestMapping(value = "/view/{id}")
    public UserInfo view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView();
        UserInfo userInfo = userInfoService.getById(id);
        return userInfo;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelMap delete(@PathVariable Integer id) {
        ModelMap result = new ModelMap();
        userInfoService.deleteById(id);
        result.put("msg", "删除成功!");
        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelMap save(UserInfo userInfo) {
        ModelMap result = new ModelMap();
        String msg = userInfo.getId() == null ? "新增成功!" : "更新成功!";
        userInfoService.save(userInfo);
        result.put("userInfo", userInfo);
        result.put("msg", msg);
        return result;
    }
    
    @RequestMapping("/login")
    @ResponseBody
    public ResultModel login(String username ,String password ) {
    	UserInfo login = userInfoService.login(username, password);
//    	if(login!=null){
    		TokenModel createToken = tokenManager.createToken(username);
    		 ResultModel setData = new ResultModel();
    				 setData.setCode(HttpStatus.OK.value());
    				 setData.setData(createToken);
    		 System.out.println("setData :"+setData);
    		return setData;
//    	}else{
//    		return null;
//    		
//    	}
    }
}

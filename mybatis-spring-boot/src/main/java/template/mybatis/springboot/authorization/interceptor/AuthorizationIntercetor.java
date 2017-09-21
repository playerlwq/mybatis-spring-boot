package template.mybatis.springboot.authorization.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.druid.util.StringUtils;
import com.sun.tools.javac.util.Constants;
import com.xiaoleilu.hutool.lang.Console;

import template.mybatis.springboot.authorization.annotation.Authorization;
import template.mybatis.springboot.authorization.manager.impl.RedisTokenManager;
import template.mybatis.springboot.authorization.manager.impl.TokenManager;
/**
 *   
 *   1，判断请求是否要权限认证 
 *   2.进行redis权限认证
 *   
 */
@Component
public class AuthorizationIntercetor extends HandlerInterceptorAdapter {

	
	@Autowired
	TokenManager redisTokenManager;
	
	/**
	 * This implementation always returns {@code true}.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//映射的不是方法
		if(!(handler instanceof HandlerMethod)){
			
			return true;
		}
		HandlerMethod handlerMethod =(HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		//得到该处理器是否需要认证
		Authorization annotation = method.getAnnotation(Authorization.class);
		if(annotation==null){
			return true;
		}else{
			   String authorization = request.getHeader(template.mybatis.springboot.model.Constants.AUTHORIZATION);
			   if(!StringUtils.isEmpty(authorization)){
				   
				   boolean checkToken = redisTokenManager.checkToken(authorization);
				   if(checkToken){
					   return true;
				   }else{
					   returnJson(response, "授权失败");
				   }
				   
			   }else{
				   returnJson(response, "该接口未认证或者未签名或者不存在此资源");
			   }
			   
		}
		
		
		
		
		
		return true;
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterConcurrentHandlingStarted(
			HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	}
	
	
	/**
     * 异常返回
     * @param response
     * @param json
     * @throws Exception
     */
    private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }

}

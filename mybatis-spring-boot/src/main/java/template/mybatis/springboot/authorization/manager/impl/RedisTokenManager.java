package template.mybatis.springboot.authorization.manager.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.xiaoleilu.hutool.crypto.SecureUtil;
import com.xiaoleilu.hutool.json.JSONUtil;

import lombok.Data;
import net.minidev.json.JSONObject;
import template.mybatis.springboot.authorization.model.TokenModel;

/**
 *  
 *  token redis Manager
 *
 */
@Component
@Data
public class RedisTokenManager implements TokenManager{
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	
	
	@Override
	public TokenModel createToken(String userId) {
		String token = SecureUtil.simpleUUID();
		TokenModel TokenModel=new TokenModel(userId, token);
		String value = JSONUtil.toJsonStr(TokenModel);
		redisTemplate.boundValueOps(token)
		.set(value, template.mybatis.springboot.model.Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS); //一天过期时间
		return TokenModel;
	}

	@Override
	public boolean checkToken(String token) {
		//
		if(!(StringUtils.isEmpty(token))){
			String json = redisTemplate.boundValueOps(token).get();
			com.xiaoleilu.hutool.json.JSONObject parseObj = JSONUtil.parseObj(json);
			TokenModel bean = parseObj.toBean(TokenModel.class);
			if(token.equals(bean.getToken())){
				//程序授权过期时间
				return	 redisTemplate.boundValueOps(token)	.expire(template.mybatis.springboot.model.Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
			}
		}
		
		return false;
	}

	@Override
	public TokenModel getToken(String authentication) {
		//获取
				String json = redisTemplate.boundValueOps(authentication).get();
//				JSONUtil.
				com.xiaoleilu.hutool.json.JSONObject parseObj = JSONUtil.parseObj(json);
				TokenModel bean = parseObj.toBean(TokenModel.class);
				return bean;
	}

	@Override
	public void deleteToken(String authentication) {
		redisTemplate.delete(authentication);
	}

}

package template.mybatis.springboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import template.mybatis.springboot.mapper.UserInfoMapper;
import template.mybatis.springboot.model.UserInfo;
import template.mybatis.springboot.service.UserInfoService;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	 @Autowired
	    private UserInfoMapper userInfoMapper;

	    public List<UserInfo> getAll(UserInfo UserInfo) {
	        if (UserInfo.getPage() != null && UserInfo.getRows() != null) {
	            PageHelper.startPage(UserInfo.getPage(), UserInfo.getRows());
	        }
	        return userInfoMapper.selectAll();
	    }

	    public UserInfo getById(Integer id) {
	        return userInfoMapper.selectByPrimaryKey(id);
	    }

	    public void deleteById(Integer id) {
	        userInfoMapper.deleteByPrimaryKey(id);
	    }

	    public void save(UserInfo country) {
	        if (country.getId() != null) {
	            userInfoMapper.updateByPrimaryKey(country);
	        } else {
	            userInfoMapper.insert(country);
	        }
	    }

		@Override
		public UserInfo login(String username ,String password){
			
			Example example=new Example(UserInfo.class); 
			example.createCriteria().andEqualTo("username",username)
			.andEqualTo("password",password);
			List<UserInfo> selectByExample = userInfoMapper.selectByExample(example);
			if(selectByExample!=null&&selectByExample.size()>0){
				return selectByExample.get(0);
			}
			return null;
		}
}

package com.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.common.util.BaseService;
import com.user.dao.UserMapper;
import com.user.entity.User;
import com.user.service.UserService;

@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames="userCache") // 本类内方法指定使用缓存时，默认的名称就是userCache
public class UserServiceImpl extends BaseService implements UserService {

	@Autowired	
	private UserMapper userMapper;
	@Override
	public List<User> listUser() {
		return userMapper.select();
	}
	
	// 因为必须要有返回值，才能保存到数据库中，如果保存的对象的某些字段是需要数据库生成的，  
	//那保存对象进数据库的时候，就没必要放到缓存了  
	@CachePut(key="#p0.id")  //#p0表示第一个参数  
	@Transactional(readOnly = false)
	@Override
	public User put(String id, User user) {
		user.setId(id);
		userMapper.put(user);
		return this.userMapper.selectByPrimaryKey(id);  
	}
	@CacheEvict(key="#p0")  //删除缓存名称为userCache,key等于指定的id对应的缓存  
	@Transactional(readOnly = false)
	@Override
	public void remove(String id) {
		userMapper.deleteById(id);
	}

	@Cacheable(key="#p0") // @Cacheable 会先查询缓存，如果缓存中存在，则不执行方法 
	@Override
	public User get(String id) {
		return userMapper.selectByPrimaryKey(id);
	}
	
	//可能只是更新某几个字段而已，所以查次数据库把数据全部拿出来全部  
	@CachePut(key="#p0.id")  
	@Transactional(readOnly = false)
	@Override
	public User update(String id, User u) {
		u.setId(id);
		userMapper.updateById(u);
		return userMapper.selectByPrimaryKey(id);
	}
	
	@CachePut(key="#p0.id")  
	@Transactional(readOnly = false)
	@Override
	public User update(User u) {
		userMapper.update(u);
		return userMapper.selectByPrimaryKey(u.getId());
	}

	@Cacheable(key="#p0") // @Cacheable 会先查询缓存，如果缓存中存在，则不执行方法 
	@Override
	public User selectByPrimaryKey(String userid) {
		return userMapper.selectByPrimaryKey(userid);
	}

}

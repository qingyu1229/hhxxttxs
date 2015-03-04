package com.design.pattern;

import java.util.List;

public interface UserDao {
	/**
	 * 获取所有User
	 * @return
	 */
	public List<User> getAllUser();
	
	/**
	 * 添加一个User
	 * @return
	 */
	public boolean addUser(User user);
	
	
}

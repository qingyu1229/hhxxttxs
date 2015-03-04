package com.design.pattern;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
	
	//此处相当于数据库了
	List<User> userList=new ArrayList<User>();
	
	public UserDaoImpl(){
		User user1=new User("u1",12);
		User user2=new User("u2",13);
		userList.add(user1); 
		userList.add(user2); 
	}

	@Override
	public List<User> getAllUser() {
		return userList;
	}

	@Override
	public boolean addUser(User user) {
		return userList.add(user);
	}

}

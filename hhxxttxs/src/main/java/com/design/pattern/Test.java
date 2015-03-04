package com.design.pattern;

import java.util.List;

public class Test {
	
	public static void main(String[] args) {
		UserDao userDao=new UserDaoImpl();
		
		User user=new User("u3",15);
		userDao.addUser(user);
		
		List<User> userList =userDao.getAllUser();
		
		for(User u:userList){
			System.out.println(u.toString());
		}
		/**
		 * 输出：
		 * 	User [name=u1, age=12]
		 *  User [name=u2, age=13]
		 *	User [name=u3, age=15]
		 */
	}
}

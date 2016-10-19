package service;

import dao.UserDao;

public class RegisterService {
	public boolean register(String username, String password) {
		UserDao user = new UserDao();
		return user.register(username, password);
	}
}

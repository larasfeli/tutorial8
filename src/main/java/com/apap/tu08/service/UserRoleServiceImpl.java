package com.apap.tu08.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.apap.tu08.model.UserRoleModel;
import com.apap.tu08.repository.UserRoleDb;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRoleDb userDb;
	
	@Override
	public UserRoleModel adduser(UserRoleModel user) {
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}
	
	@Override
	public String encrypt(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}	
	
	@Override
    public UserRoleModel findUserByUsername(String username) {
        return userDb.findByUsername(username);
    }

	@Override
	public Optional<UserRoleModel> getDetailUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}

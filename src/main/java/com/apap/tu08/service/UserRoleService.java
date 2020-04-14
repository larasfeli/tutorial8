package com.apap.tu08.service;

import java.util.Optional;

import com.apap.tu08.model.UserRoleModel;

public interface UserRoleService {
	UserRoleModel adduser(UserRoleModel user);
	public String encrypt(String password);
	Optional<UserRoleModel> getDetailUserById(Long id);
	UserRoleModel findUserByUsername(String username);

}

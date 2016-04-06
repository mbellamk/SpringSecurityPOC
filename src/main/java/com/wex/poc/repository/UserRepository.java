package com.wex.poc.repository;

import java.util.List;

import com.wex.poc.model.Permission;
import com.wex.poc.model.PermissionSetPermission;
import com.wex.poc.model.User;
import com.wex.poc.model.UserPermission;

public interface UserRepository {

	User getUserByUserName(String userName);

	public List<PermissionSetPermission> getAuthoritiesByUserName(String userName);

}

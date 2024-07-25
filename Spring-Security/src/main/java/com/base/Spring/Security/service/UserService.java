package com.base.Spring.Security.service;

import com.base.Spring.Security.dto.SaveUser;
import com.base.Spring.Security.persistense.entity.security.User;

public interface UserService {

    User registerOneCustomer(SaveUser newUser);

}

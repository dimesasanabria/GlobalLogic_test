package com.globalogic.test.service;

import com.globalogic.test.entity.User;

public interface UserService {
    public User saveService(User usuario);

    public User getUser(String token, Long id);
}

package com.yih.service;

import com.yih.db.UserRepo;
import com.yih.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Transactional
    public List<User> getUsers() {

        return userRepo.findAll();
    }

    public User getUserById(Long id) {
        return userRepo.findOne(id);
    }
}
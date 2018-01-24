package com.yih.db;

import com.yih.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepo extends PagingAndSortingRepository<User, Long> {
    List<User> findAll();
}

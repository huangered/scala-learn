package com.yih.db;

import com.yih.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepo extends PagingAndSortingRepository<User, Long> {
    @Query(value = "SELECT p FROM User p inner JOIN p.imgs")
    List<User> findAll();
}

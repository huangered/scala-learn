package com.yih.db;

import com.yih.model.user.WebUser;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface WebUserRepo extends PagingAndSortingRepository<WebUser, Long> {
    List<WebUser> findByUsernameAndPassword(String username, String password);

}

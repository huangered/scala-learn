package com.yih.db;

import com.yih.model.Img;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImgRepo extends PagingAndSortingRepository<Img, Long> {
    @Query(value = "SELECT p FROM Img p inner JOIN p.user")
    Page<Img> findAll(Pageable pageable);
}

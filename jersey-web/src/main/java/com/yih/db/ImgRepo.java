package com.yih.db;

import com.yih.model.Img;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImgRepo extends PagingAndSortingRepository<Img, Long> {
}

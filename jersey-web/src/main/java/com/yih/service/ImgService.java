package com.yih.service;

import com.yih.db.ImgRepo;
import com.yih.db.UserRepo;
import com.yih.model.Img;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
@Service
public class ImgService {

    final int SIZE = 20;

    @Autowired
    ImgRepo repo;

    @Autowired
    UserRepo userRepo;


    @Transactional
    public List<Img> getImgs(Integer page) {
        Pageable pageable = new PageRequest(page, SIZE, Sort.Direction.DESC, "id");
        Page<Img> imgs = repo.findAll(pageable);
        return imgs.getContent();
    }
}
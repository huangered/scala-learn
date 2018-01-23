package com.yih.service;

import com.yih.db.ImgRepo;
import com.yih.model.Img;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
@Service
public class ImgService {
    List<Img> imgs = Collections.synchronizedList(new ArrayList<Img>());

    @Autowired
    ImgRepo repo;

    @PostConstruct
    public void init() {
        imgs.add(new Img("Joe", "Hello"));
        imgs.add(new Img("Jane", "Spring boot is cool !"));
    }



    public List<Img> getImgs(Integer page) {
        Pageable pageable = new PageRequest(page, 20, Sort.Direction.DESC, "id");
        Page<Img> imgs = repo.findAll(pageable);
        return imgs.getContent();
    }


}
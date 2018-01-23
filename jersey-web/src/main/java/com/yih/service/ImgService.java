package com.yih.service;

import com.yih.model.Img;
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

    @PostConstruct
    public void init() {
        imgs.add(new Img("Joe", "Hello"));
        imgs.add(new Img("Jane", "Spring boot is cool !"));
    }

    public List<Img> getImgs() {
        return imgs;
    }


}
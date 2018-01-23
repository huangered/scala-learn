package com.yih.service;

import com.yih.model.Joke;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
@Service
public class JokeService {
    List<Joke> messages = Collections.synchronizedList(new ArrayList<>());

    @PostConstruct
    public void init() {
        messages.add(new Joke("Joe", "Hello"));
        messages.add(new Joke("Jane", "Spring boot is cool !"));
    }

    public List<Joke> getMessages() {
        return messages;
    }


}
package com.example.securityhibernate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TestRedis implements CommandLineRunner {

    @Autowired
    private RedisTemplate template;

    @Override
    public void run(String... args) throws Exception {
        template.opsForValue().set("pmc", "pmc hcm");

        Set<String> keys = template.keys("*");

        keys.forEach(System.out::println);
    }

}

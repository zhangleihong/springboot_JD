package com.example.springboot_init.dao;

import com.example.springboot_init.po.Comment;
import com.example.springboot_init.po.FirstClass;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FirstClassRepository extends MongoRepository<FirstClass,String> {
}

package com.example.springboot_init.dao;

import com.example.springboot_init.po.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment,String> {

}
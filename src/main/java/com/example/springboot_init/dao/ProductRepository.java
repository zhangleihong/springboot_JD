package com.example.springboot_init.dao;
import com.example.springboot_init.po.Product;
import com.example.springboot_init.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {

    Page<Product> findByFirstClass(String firstClass, Pageable pageable);
}

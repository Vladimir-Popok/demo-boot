package com.geekbrains.demoboot.repositories;

import com.geekbrains.demoboot.entities.Product;
import com.geekbrains.demoboot.entities.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{
    User findOneByUsername(String username);

    List<User> findAllByUsername(String username);
}

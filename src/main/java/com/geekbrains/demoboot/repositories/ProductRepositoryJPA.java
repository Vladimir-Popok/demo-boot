package com.geekbrains.demoboot.repositories;

import com.geekbrains.demoboot.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepositoryJPA extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByTitleContainsIgnoreCase(String word);
    Product findProductsById(Long id);

    @Query("SELECT p FROM Product p ORDER BY p.views DESC")
    public List<Product> get3TopProducts();
}

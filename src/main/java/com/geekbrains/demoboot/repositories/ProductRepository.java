package com.geekbrains.demoboot.repositories;

import com.geekbrains.demoboot.entities.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepository {
    private List<Product> products;

    @PostConstruct
    public void init() {
        products = new ArrayList<>();
        products.add(new Product(1L, "Bread", 40));
        products.add(new Product(2L, "Milk", 90));
        products.add(new Product(3L, "Cheese", 200));
    }

    public List<Product> findAll() {
        return products;
    }

    public Product findByTitle(String title) {
        return products.stream().filter(p -> p.getTitle().equals(title)).findFirst().get();
    }

    public Product findById(Long id) {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst().get();
    }

    public void save(Product product) {
        products.add(product);
    }
    public void remove(Long id) { products.remove(id.intValue() - 1); }


    public void update(Product product) {
        products.remove(product.getId().intValue() - 1);
        products.add(product);
    }
}

package com.geekbrains.demoboot.services;

import com.geekbrains.demoboot.entities.Product;
import com.geekbrains.demoboot.repositories.ProductRepository;
import com.geekbrains.demoboot.repositories.ProductRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsService {
    //private ProductRepository productRepository;
    private ProductRepositoryJPA productRepository;
    private RestTemplate restTemplate;

    @Autowired
    public void setProductRepository(ProductRepositoryJPA productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.productRepository = productRepository;
    }

    public ProductsService() {
    }

    public ProductsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return productRepository.findProductsById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> getProductsWithPagingAndFiltering(Specification<Product> specification, Pageable pageable) {
        return productRepository.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public List<Product> getFilterList(String word) {
        if (word == null) return getAllProducts();
        return productRepository.findAllByTitleContainsIgnoreCase(word);
    }

    public void add(Product product) { productRepository.save(product); }
    public void delete(Long id) { productRepository.deleteById(id); }

    public void update(Product product) { productRepository.save(product); }

    public Product incrementViewsCounter(Product product) {
        product.setViews(product.getViews() + 1);
        productRepository.save(product);
        return product;
    }

    public List<Product> getTop3List() {
        return productRepository.get3TopProducts().stream().limit(3).collect(Collectors.toList());
    }
}

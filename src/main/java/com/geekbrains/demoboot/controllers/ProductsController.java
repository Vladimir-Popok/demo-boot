package com.geekbrains.demoboot.controllers;

import com.geekbrains.demoboot.entities.Product;
import com.geekbrains.demoboot.entities.User;
import com.geekbrains.demoboot.repositories.UserRepository;
import com.geekbrains.demoboot.repositories.specification.ProductSpecs;
import com.geekbrains.demoboot.services.ProductsService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductsController {
    private ProductsService productsService;
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public String showProductsList(Model model,
                                   Principal principal,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "word", required = false) String word,
                                   @RequestParam(value="minPrice", required = false) Integer minPrice,
                                   @RequestParam(value="maxPrice", required = false) Integer maxPrice
    ) {
        Specification<Product> specification = Specification.where(null);

        if (principal != null) {
            User user = userRepository.findOneByUsername(principal.getName());
            model.addAttribute("name", user.getUsername());
            System.out.println(user.getRoles());
        }
        
        if (page == null) {
            page = 1;
        }
        if (word != null) {
            specification = specification.and(ProductSpecs.titleContains(word));
        }

        if (minPrice != null) {
            specification = specification.and(ProductSpecs.priceGreaterThanOrEq(minPrice));
        }

        if (maxPrice != null) {
            specification = specification.and(ProductSpecs.priceLesserThanOrEq(maxPrice));
        }

        Product product = new Product();
        model.addAttribute("products", productsService.getProductsWithPagingAndFiltering(specification, PageRequest.of(page - 1, 2)).getContent());
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("product", product);
        model.addAttribute("word", word);
        model.addAttribute("top3List", productsService.getTop3List());

        return "products";
    }
    @Secured("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public String addProduct(@ModelAttribute(value = "product")Product product) {
        productsService.add(product);
        return "redirect:/products";
    }

    @Secured("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteProduct(Model model, @PathVariable(value = "id") Long id) {
        productsService.delete(id);
        return "redirect:/products";
    }

    @GetMapping("/show/{id}")
    public String showOneProduct(Model model, @PathVariable(value = "id") Long id) {
        Product product = productsService.getById(id);
        productsService.incrementViewsCounter(product);
        model.addAttribute("product", product);
        return "product-page";
    }

    @Secured("hasRole('ROLE_ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditPage(Model model, @PathVariable(value = "id") Long id) {
        Product product = productsService.getById(id);
        model.addAttribute("product", product);
        return "product-edit";
    }

    @Secured("hasRole('ROLE_ADMIN')")
    @PostMapping("/edit/")
    public String editProduct(@ModelAttribute(value = "product")Product product) {
        productsService.update(product);
        return "redirect:/products";
    }
}

package org.skypro.skyshop.model.controller;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.service.BasketService;
import org.skypro.skyshop.model.service.SearchService;
import org.skypro.skyshop.model.searchResult.SearchResult;
import org.skypro.skyshop.model.service.StorageService;
import org.skypro.skyshop.model.userBasket.UserBasket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


@RestController
public class ShopController {
    private final StorageService storageService;
    private final SearchService searchService;
    private final BasketService basketService;


    public ShopController(StorageService storageService, SearchService searchService,
                           BasketService basketService) {
        this.storageService = storageService;
        this.searchService = searchService;
        this.basketService = basketService;
    }

    @GetMapping("/products")
    public Collection<Product> getAllProducts1() {
        return storageService.getAllProducts();
    }

    @GetMapping("/articles")
    public Collection<Article> getAllArticles1() {
        return storageService.getAllArticles();
    }

    @GetMapping("/search")
    public List<SearchResult> search1(@RequestParam("pattern") String pattern) {
        return searchService.search(pattern);
    }
    @GetMapping("/basket/{id}")
    public String addProduct(@PathVariable("id") UUID id){
        basketService.addInBasketById(id);
        return "Продукт успешно добавлен";
    }
    @GetMapping("/basket")
    public UserBasket getUserBasket(){
        return basketService.getUserBasket();
    }





}
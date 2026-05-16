package org.skypro.skyshop.model.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> productsMap = new HashMap<>();
    private final Map<UUID, Article> articlesMap = new HashMap<>();

    public StorageService() {
        fillTestData();
    }


    private void fillTestData() {
        UUID productId = UUID.randomUUID();
        productsMap.put(productId, new SimpleProduct("Ноутбук", 50000, productId));
        UUID productId1 = UUID.randomUUID();
        productsMap.put(productId1, new SimpleProduct("Папа может ", 320, productId1));
        UUID productId2 = UUID.randomUUID();
        productsMap.put(productId2, new SimpleProduct("Булка столичная ", 32, productId2));


        UUID articleId = UUID.randomUUID();
        articlesMap.put(articleId, new Article("Обзор ноутбука", "В цепких лапах", articleId));
        UUID articleId1 = UUID.randomUUID();
        articlesMap.put(articleId1, new Article("Обзор Игры", "Дота 2 это диагноз?", articleId1));
        UUID articleId2 = UUID.randomUUID();
        articlesMap.put(articleId2, new Article("Обзор Фильма", "BADCOMIDIAN", articleId2));
    }

    public List<Searchable> getAllSearchable() {
        List<Searchable> searchableList = new ArrayList<>();
        searchableList.addAll(productsMap.values());
        searchableList.addAll(articlesMap.values());
        return searchableList;
    }

    public Collection<Product> getAllProducts() {
        return productsMap.values();
    }

    public Collection<Article> getAllArticles() {
        return articlesMap.values();
    }


}
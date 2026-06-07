package org.skypro.skyshop.model.service;

import org.skypro.skyshop.model.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basketItem.BasketItem;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.userBasket.UserBasket;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BasketService {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket basket,StorageService storageService) {
        this.productBasket = basket;
        this.storageService = storageService;
    }

    public void addInBasketById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID не может быть null");
        }
        Product product = storageService.getProductById(id)
                .orElseThrow(NoSuchProductException::new);
        productBasket.addToBasket(product.getId());
    }

//    Внутри метода getUserBasket вам нужно:
//    обратиться к компоненту корзины ProductBasket, получить мапу товаров из корзины
//    и преобразовать ее в UserBasket.
//    В этом вам снова поможет StreamAPI и StorageSerivce.

    public UserBasket getUserBasket() {
        List <BasketItem> basketItems = productBasket.getProductBasket().entrySet().stream()
                .map(entry -> new BasketItem(
                        storageService.getProductById(entry.getKey())
                                .orElseThrow(()->new NoSuchProductException("Product not found: " + entry.getKey())),
                        entry.getValue()
                ))
                .collect(Collectors.toList());
        return new UserBasket(basketItems);
    }
}

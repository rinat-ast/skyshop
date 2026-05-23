package org.skypro.skyshop.model.basket;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@SessionScope
public class ProductBasket {
    private final Map<UUID, Integer> productBasket = new HashMap<>();


//    Метод добавления продукта в корзину. Он будет принимать UUID id
//    и не будет ничего возвращать.

    public void addToBasket (UUID id){
        productBasket.merge(id, 1, Integer::sum);
    }

    public Map<UUID, Integer> getProductBasket() {
        return Collections.unmodifiableMap(productBasket);
    }

    @Override
    public String toString() {
        return "ProductBasket{" +
                "productBasket=" + productBasket +
                '}';
    }
}
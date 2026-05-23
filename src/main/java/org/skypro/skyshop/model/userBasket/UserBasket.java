package org.skypro.skyshop.model.userBasket;

import org.skypro.skyshop.model.basketItem.BasketItem;

import java.util.List;

public final class UserBasket {
    private List<BasketItem> items;
    private double total;

    public UserBasket(List<BasketItem> list) {
        this.items = List.copyOf(list);
        // Подсчёт общей стоимости с помощью Stream API
        this.total = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }
}

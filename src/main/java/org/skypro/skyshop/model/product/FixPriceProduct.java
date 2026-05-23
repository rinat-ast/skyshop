package org.skypro.skyshop.model.product;

import java.util.Objects;
import java.util.UUID;

public class FixPriceProduct extends Product {
    private final static int FIXED_PRICE = 100;

    public FixPriceProduct(String name, UUID id) {
        super(name,id);

    }

    @Override
    public int getPrice() {
        return FIXED_PRICE;
    }

    @Override
    public String toString() {
        return super.getName() + " : " + getPrice();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }
        FixPriceProduct product = (FixPriceProduct) o;
        return Objects.equals(getName(), product.getName());

    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }




}
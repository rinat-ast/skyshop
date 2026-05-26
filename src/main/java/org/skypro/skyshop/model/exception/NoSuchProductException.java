package org.skypro.skyshop.model.exception;

import org.skypro.skyshop.model.errors.ShopError;

public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException() {
        super("Нет такого продукта");
    }



    public NoSuchProductException(String message) {
        super(message);
    }
}

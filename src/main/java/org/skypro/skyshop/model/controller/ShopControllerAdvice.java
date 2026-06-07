package org.skypro.skyshop.model.controller;

import org.skypro.skyshop.model.errors.ShopError;
import org.skypro.skyshop.model.exception.NoSuchProductException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ShopControllerAdvice {
    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<ShopError> errorReport(NoSuchProductException e){

        ShopError error = new ShopError("404", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//        return ResponseEntity(new ShopError("404",
//                "Found — сервер не нашел ничего, соответствующего запрошенному адресу"));
    }
}

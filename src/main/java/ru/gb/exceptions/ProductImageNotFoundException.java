package ru.gb.exceptions;

import lombok.Getter;

import java.io.FileNotFoundException;

@Getter
public class ProductImageNotFoundException extends FileNotFoundException {

    public ProductImageNotFoundException (String message){
        super(message);
    }

}

package ru.gb.gbexternalapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.gbexternalapi.dto.ProductDto;
import ru.gb.gbexternalapi.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ProductService productService;

    @GetMapping("/all")
    public List<ProductDto> getAll (){
        return productService.getProductList();
    }

}

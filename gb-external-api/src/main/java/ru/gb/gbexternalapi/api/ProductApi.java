package ru.gb.gbexternalapi.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbexternalapi.dto.ProductDto;
import ru.gb.gbexternalapi.dto.ProductManufacturerDto;

import java.util.List;

public interface ProductApi {
    @GetMapping
    List<ProductDto> getProductList();

    @GetMapping("/info")
    List<ProductManufacturerDto> getInfoProductList();

    @GetMapping("/{productId}")
    ResponseEntity<?> getProduct(@PathVariable("productId") Long id);

    @PostMapping
    ResponseEntity<?> handlePost(@Validated @RequestBody ProductDto productDto);

    @PutMapping("/{productId}")
    ResponseEntity<?> handleUpdate(@PathVariable("productId") Long id, @Validated @RequestBody ProductDto productDto);

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("productId") Long id);
}

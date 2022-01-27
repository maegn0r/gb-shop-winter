package ru.gb.gbexternalapi.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbexternalapi.dto.CategoryDto;

import java.util.List;


public interface CategoryApi {
    @GetMapping
    List<CategoryDto> getCategoryList();

    @GetMapping("/{categoryId}")
    ResponseEntity<?> getCategory(@PathVariable("categoryId") Long id);

    @PostMapping
    ResponseEntity<?> handlePost(@Validated @RequestBody CategoryDto categoryDto);

    @PutMapping("/{categoryId}")
    ResponseEntity<?> handleUpdate(@PathVariable("categoryId") Long id,
                                          @Validated @RequestBody CategoryDto categoryDto);

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("categoryId") Long id);
}

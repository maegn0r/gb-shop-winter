package ru.gb.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.api.category.dto.CategoryDto;
import ru.gb.api.product.dto.ProductDto;
import ru.gb.dao.CategoryDao;
import ru.gb.dao.ManufacturerDao;
import ru.gb.entity.Category;
import ru.gb.entity.Manufacturer;
import ru.gb.entity.Product;
import ru.gb.web.dto.ProductManufacturerDto;

import java.util.NoSuchElementException;

@Mapper(uses = ManufacturerMapper.class)
public interface ProductMapper {
    Product toProduct(ProductDto productDto, @Context ManufacturerDao manufacturerDao, @Context CategoryDao categoryDao);

    ProductDto toProductDto(Product product);

    @Mapping(source = "manufacturer", target = "manufacturerDto")
    ProductManufacturerDto toProductManufacturerDto(Product product);

    default Manufacturer getManufacturer(String manufacturer, @Context ManufacturerDao manufacturerDao) {
        return manufacturerDao.findByName(manufacturer).orElseThrow(NoSuchElementException::new);
    }

    default String getManufacturer(Manufacturer manufacturer) {
        return manufacturer.getName();
    }

    default Category getCategory(CategoryDto categoryDto, @Context CategoryDao categoryDao) {
        return categoryDao.findById(categoryDto.getCategoryId()).orElseThrow(NoSuchElementException::new);
    }

    default CategoryDto getCategory(Category category) {
        return CategoryDto.builder()
                .categoryId(category.getId())
                .title(category.getTitle())
                .build();
    }

}

package ru.gb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.gb.api.category.dto.CategoryDto;
import ru.gb.api.manufacturer.dto.ManufacturerDto;
import ru.gb.entity.Manufacturer;
import ru.gb.service.CategoryService;
import ru.gb.service.ManufacturerService;

@SpringBootApplication
public class GbShopWinterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GbShopWinterApplication.class, args);
    }

    @Bean
    public CommandLineRunner bootstrap(CategoryService categoryService, ManufacturerService manufacturerService) {
        return (args) -> {
//            categoryService.save(CategoryDto.builder().title("Мясные полуфабрикаты").build());
//            categoryService.save(CategoryDto.builder().title("Овощи").build());
//            categoryService.save(CategoryDto.builder().title("Детское питание").build());
//            manufacturerService.save(ManufacturerDto.builder().name("Nestle").build());
//            manufacturerService.save(ManufacturerDto.builder().name("Совхоз им. Ленина").build());
//            manufacturerService.save(ManufacturerDto.builder().name("Мираторг").build());
        };
    }
}

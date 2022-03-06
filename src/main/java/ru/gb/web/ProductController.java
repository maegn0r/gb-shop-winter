package ru.gb.web;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.product.dto.ProductDto;
import ru.gb.choosen.ChosenCategoriesDto;
import ru.gb.dao.ProductImageDao;
import ru.gb.dao.security.AccountUserDao;
import ru.gb.entity.Product;
import ru.gb.entity.security.AccountUser;
import ru.gb.exceptions.ProductImageNotFoundException;
import ru.gb.service.CategoryService;
import ru.gb.service.ManufacturerService;
import ru.gb.service.ProductImageService;
import ru.gb.service.ProductService;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;
    private final ProductImageService productImageService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public String getProductList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/product-list";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('product.create', 'product.update')")
    public String showForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        ProductDto productDto;
        if (id != null) {
            productDto = productService.findById(id);
        } else {
            productDto = new ProductDto();
        }
        model.addAttribute("product", productDto);
        model.addAttribute("categoriesList", categoryService.findAll());
        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("chosenCategories", new ChosenCategoriesDto(new HashSet<>()));
        return "product/product-form";
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('product.read')")
    public String info(Model model, @PathVariable(name = "productId") Long id) {
        ProductDto productDto;
        if (id != null) {
            productDto = productService.findById(id);
        } else {
            return "redirect:/product/all";
        }
        model.addAttribute("product", productDto);
        return "product/product-info";
    }

    // @DateTimeFormat если будут проблемы с получением даты из шаблона подставитьт эту аннотацию
    @PostMapping
    @PreAuthorize("hasAnyAuthority('product.create', 'product.update')")
    public String saveProduct(@ModelAttribute ProductDto product, @ModelAttribute ChosenCategoriesDto chosenCategoriesDto) {
        product.setCategories(chosenCategoriesDto.getChosenCategory().stream().map(categoryService::findById).collect(Collectors.toSet()));
        product.setManufactureDate(LocalDate.now());
        productService.save(product);
        return "redirect:/product/all";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAnyAuthority('product.delete')")
    public String deleteById(@RequestParam(name = "id") Long id) {
        productService.deleteById(id);
        return "redirect:/product/all";
    }

    // todo ДЗ* - сделать поддержку множества картинок для для страницы подробной информации с продуктами
    @GetMapping(value = "images/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(productImageService.loadFileAsResource(id), "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            if (e instanceof ProductImageNotFoundException){
                throw new ProductImageNotFoundException("For product with ID ="+ id +" "+ e.getMessage());
            } else {
                throw e;
            }
        }
    }
}

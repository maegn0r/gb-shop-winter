package ru.gb.web.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.api.category.dto.CategoryDto;
import ru.gb.api.manufacturer.dto.ManufacturerDto;
import ru.gb.api.product.dto.ProductDto;
import ru.gb.api.product.dto.enums.Status;
import ru.gb.dao.ProductDao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerSpringBootTest {

    final private static String PRODUCT_TITLE = "Apple";
    final private static String UPDATED_PRODUCT_TITLE = "Green apple";

    @Autowired
    MockMvc mockMvc;


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ProductDao productDao;

    @Test
    @Order(1)
    public void handlePostCategoryTest() throws Exception {
        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .title("TestCategory")
                                        .build()
                                )
                        ))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void saveManufacturerTest() throws Exception {
        mockMvc.perform(post("/api/v1/manufacturer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ManufacturerDto.builder()
                                        .name("TestManufacturer")
                                        .build()
                                )
                        ))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    public void handlePostTest() throws Exception {
        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ProductDto.builder()
                                        .title(PRODUCT_TITLE)
                                        .cost(BigDecimal.valueOf(50))
                                        .manufactureDate(LocalDate.now())
                                        .status(Status.ACTIVE)
                                        .manufacturer("TestManufacturer")
                                        .categories(Set.of(new CategoryDto(1L,"TestCategory")))
                                        .build()
                                )
                        ))
                .andExpect(status().isCreated());

        assertEquals(1, productDao.count());
    }

    @Test
    @Order(4)
    public void getProductListTest() throws Exception {
        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value(PRODUCT_TITLE));
    }

    @Test
    @Order(5)
    public void handleUpdateTest() throws Exception {
        mockMvc.perform(put("/api/v1/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ProductDto.builder()
                                        .id(1L)
                                        .title(UPDATED_PRODUCT_TITLE)
                                        .cost(BigDecimal.valueOf(50))
                                        .manufactureDate(LocalDate.now())
                                        .status(Status.ACTIVE)
                                        .manufacturer("TestManufacturer")
                                        .categories(Set.of(new CategoryDto(1L,"TestCategory")))
                                        .build()
                                )
                        ))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    public void getProductTest() throws Exception {
        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("title").value(UPDATED_PRODUCT_TITLE));
    }

    @Test
    @Order(7)
    public void deleteByIdTest() throws Exception {
        mockMvc.perform(delete("/api/v1/product/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(8)
    public void getZeroCategoryListTest() throws Exception {
        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }
}

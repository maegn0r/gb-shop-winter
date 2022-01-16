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
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.web.dto.CategoryDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryControllerSpringBootTest {

    final private static String CATEGORY_TITLE = "Beverages";
    final private static String CATEGORY_ID = "1";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void saveCategoryTest() throws Exception {
        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .title(CATEGORY_TITLE)
                                        .build()
                                )
                        ))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void findAllCategoriesTest() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(CATEGORY_ID))
                .andExpect(jsonPath("$.[0].title").value(CATEGORY_TITLE));
    }

    @Test
    @Order(3)
    public void updateCategoryTest() throws Exception {
        mockMvc.perform(put("/api/v1/category/" + CATEGORY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .title("updated " + CATEGORY_TITLE)
                                        .build()
                                )
                        ))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(4)
    public void findCategoryByIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/category/" + CATEGORY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("title").value("updated " + CATEGORY_TITLE));
    }

    @Test
    @Order(5)
    public void deleteCategoryTest() throws Exception {
        mockMvc.perform(delete("/api/v1/category/" + CATEGORY_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    public void isCategoryDeletedTest() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @Order(7)
    public void createCategoryWithBlankTitleTest() throws Exception {
        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .title("")
                                        .build()
                                )
                        ))
                .andExpect(status().isBadRequest());
    }
}

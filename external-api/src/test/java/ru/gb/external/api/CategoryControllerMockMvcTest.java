package ru.gb.external.api;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gb.api.category.api.CategoryGateway;
import ru.gb.api.category.dto.CategoryDto;
import ru.gb.api.manufacturer.api.ManufacturerGateway;
import ru.gb.api.manufacturer.dto.ManufacturerDto;
import ru.gb.external.api.rest.CategoryController;
import ru.gb.external.api.rest.ManufacturerController;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerMockMvcTest {
    @Mock
    CategoryGateway categoryGateway;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    List<CategoryDto> categoryDtoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        categoryDtoList.add(new CategoryDto(1L, "TestCategory1"));
        categoryDtoList.add(new CategoryDto(2L, "TestCategory2"));

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void mockMvcGetManufacturerListTest() throws Exception {

        given(categoryGateway.getCategoryList()).willReturn(categoryDtoList);

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value("TestCategory1"))
                .andExpect(jsonPath("$.[1].title").value("TestCategory2"));
    }

    @Test
    public void createManufacturerTest() throws Exception {
        given(categoryGateway.handlePost(any(CategoryDto.class))).willReturn(new ResponseEntity<>(HttpStatus.CREATED));

        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"TestCategory3\"" +
                                "}"))
                .andExpect(status().isCreated());

    }

    @Test
    public void getManufacturerListTest() {

        given(categoryGateway.getCategoryList()).willReturn(categoryDtoList);

        List<CategoryDto> categoryDtoList = categoryController.getCategoryList();

        then(categoryGateway).should().getCategoryList();

        assertAll(
                () -> assertEquals(2, categoryDtoList.size(), "Size "),
                () -> assertEquals("TestCategory1", categoryDtoList.get(0).getTitle())
        );
    }
}

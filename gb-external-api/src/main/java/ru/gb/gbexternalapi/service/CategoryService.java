package ru.gb.gbexternalapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import ru.gb.gbexternalapi.api.CategoryApi;

@FeignClient(url = "localhost:8080/api/v1/category", value = "CategoryServiceApi")
public interface CategoryService extends CategoryApi {
}

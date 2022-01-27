package ru.gb.gbexternalapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import ru.gb.gbexternalapi.api.ProductApi;

@FeignClient(url = "localhost:8080/api/v1/product", value = "ProductServiceApi")
public interface ProductService extends ProductApi {
}

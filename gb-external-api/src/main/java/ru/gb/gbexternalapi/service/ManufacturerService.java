package ru.gb.gbexternalapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import ru.gb.gbexternalapi.api.ManufacturerApi;

@FeignClient(url = "localhost:8080/api/v1/manufacturer", value = "ManufacturerServiceApi")
public interface ManufacturerService extends ManufacturerApi {
}

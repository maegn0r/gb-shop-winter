package ru.gb.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    @JsonProperty(value = "id")
    private Long categoryId;
    @NotBlank
    private String title;
}

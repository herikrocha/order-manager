package com.example.ordermanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    Long id;

    @NotBlank(message = "Name must be informed!")
    String name;

    Integer stockAmount;
}

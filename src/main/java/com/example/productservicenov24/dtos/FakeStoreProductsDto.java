package com.example.productservicenov24.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductsDto {
    Long id;
    String title;
    Double price;
    String category;
    String description;
}

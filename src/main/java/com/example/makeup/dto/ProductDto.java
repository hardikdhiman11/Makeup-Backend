package com.example.makeup.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {
    private String name;
    private String batchName;
    private int count;
    private String categoryId;
    private String subCategoryId;
}

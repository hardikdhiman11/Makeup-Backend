package com.example.makeup.dto.mapper;

import com.example.makeup.dto.ProductDto;
import com.example.makeup.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id",ignore = true)
    Product productDtoToProduct(ProductDto productDto);
    ProductDto productToProductDto(Product product);
}

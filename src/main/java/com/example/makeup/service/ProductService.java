package com.example.makeup.service;

import com.example.makeup.dto.ProductDto;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    public boolean addProduct(ProductDto productDto);
    public boolean removeProduct();

}

package com.example.makeup.service.impl;

import com.example.makeup.dto.ProductDto;
import com.example.makeup.dto.mapper.ProductMapper;
import com.example.makeup.entity.Product;
import com.example.makeup.repo.ProductRepository;
import com.example.makeup.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductMapper productMapper;
    @Autowired
    private final ProductRepository productRepository;
    @Override
    public boolean addProduct(ProductDto productDto) {
        try {
            Product product = productMapper.productDtoToProduct(productDto);
            productRepository.save(product);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean removeProduct() {
        return false;
    }
}

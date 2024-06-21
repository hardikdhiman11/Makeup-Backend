package com.example.makeup.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    private long id;
    private String name;
    private String batchName;
    private int count;
    @OneToOne(fetch = FetchType.LAZY,orphanRemoval = true)
    @Column(name = "category_id")
    private Category category;
    @OneToOne(fetch = FetchType.LAZY,orphanRemoval = true)
    @Column(name = "sub_category_id")
    private SubCategory subCategory;
}

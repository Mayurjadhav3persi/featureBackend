package com.workflow2.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name="Sub_Category")
@Entity
public class SubCategory {
    @Id
    @Column(name="sub_category_name")
    String subCategoryName;

    @Column(name = "sub_category_image", unique = false, nullable = true, length = 16777215)
    private byte[] image;

    @Column(name="category")
    private String category;
}

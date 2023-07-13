package com.workflow2.ecommerce.dto;

import com.workflow2.ecommerce.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SubCategoryDTO {
    private String name;
    private byte[] image = null;


}

package com.workflow2.ecommerce.repository;

import com.workflow2.ecommerce.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubCategoryDao extends JpaRepository<SubCategory,String> {

    @Query(value="select * from sub_category p where p.category = :category", nativeQuery=true)
    List<SubCategory> findByCategory(@Param("category") String category);
}

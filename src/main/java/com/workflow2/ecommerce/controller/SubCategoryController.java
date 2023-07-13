package com.workflow2.ecommerce.controller;

import com.workflow2.ecommerce.dto.CategoryDTO;
import com.workflow2.ecommerce.entity.Category;
import com.workflow2.ecommerce.entity.SubCategory;
import com.workflow2.ecommerce.services.CategoryServiceImpl;
import com.workflow2.ecommerce.services.ProductServiceImpl;
import com.workflow2.ecommerce.services.SubCategoryService;
import com.workflow2.ecommerce.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class SubCategoryController {
    @Autowired
    SubCategoryService subCategoryService;
    @Autowired
    ProductServiceImpl productService;

    /**
     * This is the endpoint which returns all the categories from the database
     * @return It returns all the categories form the database
     */
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllSubCategory() {
        return ResponseEntity.ok().body(subCategoryService.getAllSubCategories());
    }

    /**
     * This endpoint returns the particular category by its name
     * @param name It is the name of category we wanted to find
     * @return This return a category whose name is given a path variable
     */
//    @GetMapping("/subCategory/{name}")
//    public ResponseEntity<CategoryDTO> getSubCategoryById(@PathVariable String name) {
//
//        return  ResponseEntity.ok().body(subCategoryService.getSubCategoryById(name));
//    }

    /**
     * This is the endpoint for creating new category
     * @param subCategory This contains Category name inside category class object
     * @param file This is the image of the category we wanted to create
     * @return This returns the category we have created
     */
    @PostMapping("/")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<CategoryDTO> save(@RequestPart("subCategory") SubCategory subCategory,
                                            @RequestPart("image") MultipartFile file) {
        try{
            SubCategory subCategory1 = SubCategory.builder()
                    .subCategoryName(subCategory.getSubCategoryName())
                    .image(ImageUtility.compressImage(file.getBytes()))
                    .category(subCategory.getCategory())
                    .build();

            return new ResponseEntity<CategoryDTO>(subCategoryService.saveSubCategory(subCategory1), HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * This endpoint is for deleting category by its name
     * @param name It contains the name of the category we wanted to delete
     * @return It returns the success message in side response entity class body
     */
    @DeleteMapping("/{name}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> deleteById(@PathVariable String name) {
        return ResponseEntity.ok().body(subCategoryService.deleteCategoryById(name));
    }

    /**
     * This endpoint delete all the category present in the database
     * @return It returns the success message in side response entity class body
     */
    @DeleteMapping("/")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> deleteCategories() {
        return ResponseEntity.ok().body(subCategoryService.deleteAllCategories());
    }

    @GetMapping("/category/{category}")
    public  ResponseEntity<?> getByCategory(@PathVariable("category") String category) throws Exception {
        if((subCategoryService.getAllSubCategoriesByCategory(category).isEmpty())){
            System.out.println("in if");
            return ResponseEntity.ok().body(productService.getAllProductByCategory(category));
        } else if (!(subCategoryService.getAllSubCategoriesByCategory(category).isEmpty())) {
            System.out.println("in else if");
            return ResponseEntity.ok().body(subCategoryService.getAllSubCategoriesByCategory(category));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}

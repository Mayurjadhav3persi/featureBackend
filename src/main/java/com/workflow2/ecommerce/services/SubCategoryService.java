package com.workflow2.ecommerce.services;

import com.workflow2.ecommerce.dto.CategoryDTO;
import com.workflow2.ecommerce.dto.SubCategoryDTO;
import com.workflow2.ecommerce.entity.SubCategory;
import com.workflow2.ecommerce.repository.SubCategoryDao;
import com.workflow2.ecommerce.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubCategoryService {
    @Autowired
    SubCategoryDao subCategoryDao;

    /**
     * This method is used to create category to the database
     * @param subCategory It is a Category object which have category name and image as attribute
     * @return It returns Category which we saved to the database
     */

    public CategoryDTO saveSubCategory(SubCategory subCategory) throws Exception {
        SubCategory category1 = subCategoryDao.save(subCategory);
        return CategoryDTO.builder().name(category1.getSubCategoryName()).image(ImageUtility.decompressImage(category1.getImage())).build();
    }

    /**
     * This method returns all the category presents in the database
     * @return It returns list of all the categories
     */

    public List<CategoryDTO> getAllSubCategories() {
        try {
            List<SubCategory> subCategories = subCategoryDao.findAll();
            List<CategoryDTO> decompressCats = new ArrayList<>();
            for (SubCategory cat : subCategories) {
                cat.setImage(ImageUtility.decompressImage(cat.getImage()));
                CategoryDTO prod = CategoryDTO.builder()
                        .name(cat.getSubCategoryName())
                        .image(cat.getImage())
                        .build();
                decompressCats.add(prod);
            }
            return decompressCats;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * Using this method we can delete a particular category by given name
     * @param name This is a String value which contains name of category we wanted to delete
     * @return It returns success message
     */

    public String deleteCategoryById(String name) {

        subCategoryDao.deleteById(name);
        return "Successfully deleted the category";
    }

    /**
     * This method is used to update category
     * @param subCategory This parameter have Category object which we need to update
     * @param name This parameter contains name of the category we wanted to update
     * @return It returns updated category
     */

    public CategoryDTO updateCategoryById(SubCategory subCategory, String name) throws Exception {
        final SubCategory cat = subCategoryDao.getReferenceById(name);
        subCategoryDao.save(cat);
        subCategory.setImage(ImageUtility.decompressImage(cat.getImage()));
        return CategoryDTO.builder().name(subCategory.getSubCategoryName()).image(subCategory.getImage()).build();

    }

    /**
     * This method help us to fetch the particular category by category name
     * @param name This parameter is the name of category we wanted to fetch
     * @return It returns the category whose name we have given
     */

    public CategoryDTO getSubCategoryById(String name) {
        try {
            final SubCategory cat = subCategoryDao.getReferenceById(name);
            return CategoryDTO.builder().name(cat.getSubCategoryName()).image(ImageUtility.decompressImage(cat.getImage())).build();
        }catch (Exception e){
            return null;
        }

    }

    /**
     * This method delete all the categories from the database
     * @return It returns Success message
     */

    public  String deleteAllCategories() {
        subCategoryDao.deleteAll();
        return "Deleted Successfully all Sub-Categories";
    }


    public List<SubCategoryDTO> getAllSubCategoriesByCategory(String category) throws Exception {
        System.out.println("In category" +category);
        List<SubCategory> subCategory = subCategoryDao.findByCategory(category);
        List<SubCategoryDTO> subCategoryDTOS = new ArrayList<>();
        for(SubCategory sub : subCategory){
            SubCategoryDTO subCategoryDTO = new SubCategoryDTO();
            subCategoryDTO.setImage(ImageUtility.decompressImage(sub.getImage()));
            subCategoryDTO.setName(sub.getSubCategoryName());
            subCategoryDTOS.add(subCategoryDTO);
        }

//        try{
//        for(SubCategory sub : subCategory){
//            sub.setImage(ImageUtility.decompressImage(sub.getImage()));
//
//        }
            return  subCategoryDTOS;



    }
}

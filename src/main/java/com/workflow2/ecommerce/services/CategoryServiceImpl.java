package com.workflow2.ecommerce.services;


import com.workflow2.ecommerce.dto.CategoryDTO;
import com.workflow2.ecommerce.entity.Category;


import com.workflow2.ecommerce.repository.CategoryDao;
import com.workflow2.ecommerce.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains implementation of all the methods related to category service
 * @author Mayur_Jadhav
 * @version v0.0.1
 */
@Service
public class CategoryServiceImpl  {
	@Autowired
	CategoryDao categoryDao;

	/**
	 * This method is used to create category to the database
	 * @param category It is a Category object which have category name and image as attribute
	 * @return It returns Category which we saved to the database
	 */

	public CategoryDTO saveCategory(Category category) throws Exception {
		Category category1 = categoryDao.save(category);
		return CategoryDTO.builder().name(category1.getName()).image(ImageUtility.decompressImage(category1.getImage())).build();
	}

	/**
	 * This method returns all the category presents in the database
	 * @return It returns list of all the categories
	 */

	public List<CategoryDTO> getAllCategories() {
		try {
			List<Category> categories = categoryDao.findAll();
			List<CategoryDTO> decompressCats = new ArrayList<>();
			for (Category cat : categories) {
				cat.setImage(ImageUtility.decompressImage(cat.getImage()));
				CategoryDTO prod = CategoryDTO.builder()
						.name(cat.getName())
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
		
		categoryDao.deleteById(name);
		return "Successfully deleted the category";
	}

	/**
	 * This method is used to update category
	 * @param category This parameter have Category object which we need to update
	 * @param name This parameter contains name of the category we wanted to update
	 * @return It returns updated category
	 */

	public CategoryDTO updateCategoryById(Category category, String name) throws Exception {
			final Category cat = categoryDao.getReferenceById(name);
			categoryDao.save(category);
			category.setImage(ImageUtility.decompressImage(cat.getImage()));
			return CategoryDTO.builder().name(category.getName()).image(category.getImage()).build();

	}

	/**
	 * This method help us to fetch the particular category by category name
	 * @param name This parameter is the name of category we wanted to fetch
	 * @return It returns the category whose name we have given
	 */

	public CategoryDTO getCategoryById(String name) {
		try {
			final Category cat = categoryDao.getReferenceById(name);
			return CategoryDTO.builder().name(cat.getName()).image(ImageUtility.decompressImage(cat.getImage())).build();
		}catch (Exception e){
			return null;
		}

	}

	/**
	 * This method delete all the categories from the database
	 * @return It returns Success message
	 */

	public  String deleteAllCategories() {
		categoryDao.deleteAll();
		return "Deleted Successfully all Categories";
	}

	
}


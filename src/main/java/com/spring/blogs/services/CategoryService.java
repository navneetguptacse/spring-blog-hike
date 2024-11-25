package com.spring.blogs.services;

import java.util.List;

import com.spring.blogs.payloads.CategoryTransferObject;

public interface CategoryService {
	CategoryTransferObject createCategory(CategoryTransferObject category);

	Boolean checkCategoryExistByCategoryTitle(String title);

	CategoryTransferObject updateCategory(CategoryTransferObject category, Integer categoryId);

	CategoryTransferObject getCategoryById(Integer categoryId);

	List<CategoryTransferObject> getAllCategory();

	void deleteCategory(Integer categoryId);

}

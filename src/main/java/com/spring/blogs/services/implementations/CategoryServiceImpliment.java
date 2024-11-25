package com.spring.blogs.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.blogs.exceptions.ResourceNotFoundException;
import com.spring.blogs.models.CategoryEntity;
import com.spring.blogs.payloads.CategoryTransferObject;
import com.spring.blogs.repositories.CategoryRepository;
import com.spring.blogs.services.CategoryService;

@Service
public class CategoryServiceImpliment implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryTransferObject createCategory(CategoryTransferObject categoryTransferObject) {
		CategoryEntity category = this.transferObjectToCategory(categoryTransferObject);
		CategoryEntity saved = this.categoryRepository.save(category);
		return this.categoryToTransferObject(saved);
	}

	@Override
	public Boolean checkCategoryExistByCategoryTitle(String title) {
		Boolean categoryExists = this.categoryRepository.findByTitle(title).isPresent();
		return categoryExists;
	}

	@Override
	public CategoryTransferObject updateCategory(CategoryTransferObject categoryTransferObject, Integer categoryId) {
		CategoryEntity category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		category.setTitle(categoryTransferObject.getTitle());

		String description = categoryTransferObject.getDescription();
		if (description != null && !description.trim().isEmpty()) {
			category.setDescription(description);
		}

		category = this.categoryRepository.save(category);
		return this.categoryToTransferObject(category);
	}

	@Override
	public CategoryTransferObject getCategoryById(Integer categoryId) {
		CategoryEntity category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

		return this.categoryToTransferObject(category);
	}

	@Override
	public List<CategoryTransferObject> getAllCategory() {
		List<CategoryEntity> categoryList = this.categoryRepository.findAll();
		List<CategoryTransferObject> categories = categoryList.stream()
				.map(category -> this.categoryToTransferObject(category)).collect(Collectors.toList());

		return categories;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		CategoryEntity category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		this.categoryRepository.delete(category);
	}

	// Method: Converting CategoryTransferObject to CategoryEntity
	public CategoryEntity transferObjectToCategory(CategoryTransferObject categoryTransferObject) {
		CategoryEntity category = this.modelMapper.map(categoryTransferObject, CategoryEntity.class);
		return category;
	}

	// Method: Converting CategoryEntity to CategoryTransferObject
	public CategoryTransferObject categoryToTransferObject(CategoryEntity category) {
		CategoryTransferObject categoryTransferObject = this.modelMapper.map(category, CategoryTransferObject.class);
		return categoryTransferObject;
	}

}

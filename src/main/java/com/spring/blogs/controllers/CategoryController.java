package com.spring.blogs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.blogs.exceptions.ResourceNotFoundException;
import com.spring.blogs.payloads.CategoryTransferObject;
import com.spring.blogs.responses.ApiResponse;
import com.spring.blogs.responses.ErrorResponse;
import com.spring.blogs.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/catgs")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// POST: Create Category
	@PostMapping("/")
	public ResponseEntity<ApiResponse<Object>> createCategory(
			@Valid @RequestBody CategoryTransferObject categoryTransferObject) {
		try {
			boolean categoryExists = this.categoryService
					.checkCategoryExistByCategoryTitle(categoryTransferObject.getTitle());

			if (categoryExists) {
				ErrorResponse errorResponse = new ErrorResponse("Category already exists with the same title", false,
						"CATEGORY_ALREADY_EXISTS");
				return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
			}

			CategoryTransferObject createdCategoryTransferObject = this.categoryService
					.createCategory(categoryTransferObject);
			ApiResponse<Object> response = new ApiResponse<>("Category created successfully", true,
					createdCategoryTransferObject);
			return new ResponseEntity<>(response, HttpStatus.CREATED);

		} catch (Exception e) {
			ApiResponse<Object> response = new ApiResponse<>("Failed to create category", false, null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// PUT: Update Category
	@PutMapping("/{categoryId}")
	public ResponseEntity<ApiResponse<Object>> updateCategory(
			@Valid @RequestBody CategoryTransferObject categoryTransferObject, @PathVariable Integer categoryId) {
		try {
			CategoryTransferObject updatedCategoryTransferObject = this.categoryService
					.updateCategory(categoryTransferObject, categoryId);
			ApiResponse<Object> response = new ApiResponse<>("Category updated successfully", true,
					updatedCategoryTransferObject);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (ResourceNotFoundException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), false, "CATEGORY_NOT_FOUND");
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}
	}

	// DELETE: Delete Category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse<Object>> deleteCategory(@PathVariable Integer categoryId) {
		try {
			this.categoryService.deleteCategory(categoryId);
			ApiResponse<Object> response = new ApiResponse<>("Category deleted successfully", true, null);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), false, "CATEGORY_NOT_FOUND");
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}
	}

	// GET: Get Category
	@GetMapping("/{categoryId}")
	public ResponseEntity<ApiResponse<Object>> getCategoryById(@PathVariable Integer categoryId) {
		try {
			CategoryTransferObject categoryTransferObject = this.categoryService.getCategoryById(categoryId);

			ApiResponse<Object> response = new ApiResponse<>("Category fetched successfully", true,
					categoryTransferObject);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), false, "CATEGORY_NOT_FOUND");
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}
	}

	// GET: Get All Categories
	@GetMapping("/")
	public ResponseEntity<ApiResponse<List<CategoryTransferObject>>> getAllCategories() {
		List<CategoryTransferObject> categoryTransferObjects = this.categoryService.getAllCategory();
		if (categoryTransferObjects.isEmpty()) {
			ApiResponse<List<CategoryTransferObject>> response = new ApiResponse<>("No categories found", true, null);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		ApiResponse<List<CategoryTransferObject>> response = new ApiResponse<>("Category fetched successfully", true,
				categoryTransferObjects);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}

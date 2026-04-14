package com.ecommerce.backend.controller.rest;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.request.CategoryRequestDTO;
import com.ecommerce.backend.dto.response.CategoryResponseDTO;
import com.ecommerce.backend.service.interfaces.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController extends BaseRestController<CategoryResponseDTO, CategoryRequestDTO, CategoryRequestDTO> {

    public CategoryController(CategoryService categoryService, PagedResourcesAssembler<CategoryResponseDTO> pagedResourcesAssembler) {
        super(categoryService, pagedResourcesAssembler);
    }
}

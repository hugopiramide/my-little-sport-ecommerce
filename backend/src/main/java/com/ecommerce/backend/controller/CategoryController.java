package com.ecommerce.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.backend.dto.request.CategoryRequestDTO;
import com.ecommerce.backend.dto.response.CategoryResponseDTO;
import com.ecommerce.backend.service.interfaces.CategoryService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final PagedResourcesAssembler<CategoryResponseDTO> pagedResourcesAssembler;

    public CategoryController(CategoryService categoryService, PagedResourcesAssembler<CategoryResponseDTO> pagedResourcesAssembler) {
        this.categoryService = categoryService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<?>> getAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<CategoryResponseDTO> page = categoryService.findAllPageable(pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(page));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponseDTO>> getAllNoPage() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO created = categoryService.createFromDto(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO dto) {
        return ResponseEntity.ok(categoryService.updateFromDto(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

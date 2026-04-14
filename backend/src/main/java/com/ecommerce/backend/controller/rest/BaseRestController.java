package com.ecommerce.backend.controller.rest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.ecommerce.backend.service.interfaces.BaseCrudService;

import jakarta.validation.Valid;

/**
 * @param <T>        Response DTO type.
 * @param <CreateIn> Request DTO type for creation.
 * @param <UpdateIn> Request DTO type for updates.
 */
public abstract class BaseRestController<T, CreateIn, UpdateIn> {

    private final BaseCrudService<T, CreateIn, UpdateIn, Long> service;
    private final PagedResourcesAssembler<T> pagedResourcesAssembler;

    protected BaseRestController(BaseCrudService<T, CreateIn, UpdateIn, Long> service, PagedResourcesAssembler<T> pagedResourcesAssembler) {
        this.service = service;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<T>>> getAll(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<T> page = service.findAllPageable(pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(page));
    }

    @GetMapping("/all")
    public ResponseEntity<List<T>> getAllNoPage() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<T> create(@Valid CreateIn dto) {
        T created = service.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable Long id, @Valid UpdateIn dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

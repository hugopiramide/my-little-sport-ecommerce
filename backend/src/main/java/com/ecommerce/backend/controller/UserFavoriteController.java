package com.ecommerce.backend.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.request.UserFavoriteRequestDTO;
import com.ecommerce.backend.dto.response.UserFavoriteResponseDTO;
import com.ecommerce.backend.service.interfaces.UserFavoriteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user-favorites")
public class UserFavoriteController {

    private final UserFavoriteService userFavoriteService;
    private final PagedResourcesAssembler<UserFavoriteResponseDTO> pagedResourcesAssembler;

    public UserFavoriteController(UserFavoriteService userFavoriteService, PagedResourcesAssembler<UserFavoriteResponseDTO> pagedResourcesAssembler) {
        this.userFavoriteService = userFavoriteService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserFavoriteResponseDTO>> getAll() {
        return ResponseEntity.ok(userFavoriteService.findAll());
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<UserFavoriteResponseDTO>>> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<UserFavoriteResponseDTO> page = userFavoriteService.findAllPageable(pageable);
        PagedModel<EntityModel<UserFavoriteResponseDTO>> pagedModel = pagedResourcesAssembler.toModel(page);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFavoriteResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userFavoriteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserFavoriteResponseDTO> create(@Valid @RequestBody UserFavoriteRequestDTO dto) {
        UserFavoriteResponseDTO created = userFavoriteService.createFromDto(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserFavoriteResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserFavoriteRequestDTO dto) {
        return ResponseEntity.ok(userFavoriteService.updateFromDto(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userFavoriteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


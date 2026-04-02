package com.ecommerce.backend.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.backend.dto.request.UserFavoriteRequestDTO;
import com.ecommerce.backend.dto.response.UserFavoriteResponseDTO;

public interface UserFavoriteService {

    List<UserFavoriteResponseDTO> findAll();

    Page<UserFavoriteResponseDTO> findAllPageable(Pageable pageable);

    UserFavoriteResponseDTO findById(Long id);

    UserFavoriteResponseDTO createFromDto(UserFavoriteRequestDTO userFavoriteRequestDTO);

    UserFavoriteResponseDTO updateFromDto(Long id, UserFavoriteRequestDTO userFavoriteRequestDTO);

    void deleteById(Long id);
}


package com.ecommerce.backend.service.interfaces;

import com.ecommerce.backend.dto.request.UserFavoriteRequestDTO;
import com.ecommerce.backend.dto.response.UserFavoriteResponseDTO;

public interface UserFavoriteService extends BaseCrudService<UserFavoriteResponseDTO, UserFavoriteRequestDTO, UserFavoriteRequestDTO, Long> {
}

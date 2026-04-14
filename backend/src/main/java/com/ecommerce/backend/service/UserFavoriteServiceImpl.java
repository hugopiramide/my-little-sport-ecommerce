package com.ecommerce.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.request.UserFavoriteRequestDTO;
import com.ecommerce.backend.dto.response.UserFavoriteResponseDTO;
import com.ecommerce.backend.mapper.UserFavoriteMapper;
import com.ecommerce.backend.model.UserFavorite;
import com.ecommerce.backend.repository.UserFavoriteRepository;
import com.ecommerce.backend.service.interfaces.UserFavoriteService;

@Service
public class UserFavoriteServiceImpl extends BaseCrudServiceImpl<UserFavorite, UserFavoriteResponseDTO, UserFavoriteRequestDTO, UserFavoriteRequestDTO> implements UserFavoriteService {

    private final UserFavoriteMapper userFavoriteMapper;

    public UserFavoriteServiceImpl(UserFavoriteRepository userFavoriteRepository, UserFavoriteMapper userFavoriteMapper) {
        super(userFavoriteRepository);
        this.userFavoriteMapper = userFavoriteMapper;
    }

    @Override
    protected UserFavoriteResponseDTO toDto(UserFavorite entity) {
        return userFavoriteMapper.toUserFavoriteResponseDTO(entity);
    }

    @Override
    protected List<UserFavoriteResponseDTO> toDtoList(List<UserFavorite> entities) {
        return entities.stream().map(userFavoriteMapper::toUserFavoriteResponseDTO).toList();
    }

    @Override
    protected UserFavorite toEntity(UserFavoriteRequestDTO dto) {
        return userFavoriteMapper.toEntity(dto);
    }

    @Override
    protected void updateEntity(UserFavoriteRequestDTO dto, UserFavorite target) {
        userFavoriteMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected void updateEntityFromCreate(UserFavoriteRequestDTO dto, UserFavorite target) {
        userFavoriteMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected UserFavorite newEntity() {
        return new UserFavorite();
    }

    @Override
    protected String getEntityName() {
        return "UserFavorite";
    }

    @Override
    protected void afterCreate(UserFavoriteRequestDTO dto, UserFavorite entity) {
        entity.setCreated_at(LocalDateTime.now());
    }
}

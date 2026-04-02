package com.ecommerce.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.request.UserFavoriteRequestDTO;
import com.ecommerce.backend.dto.response.UserFavoriteResponseDTO;
import com.ecommerce.backend.mapper.UserFavoriteMapper;
import com.ecommerce.backend.model.UserFavorite;
import com.ecommerce.backend.repository.UserFavoriteRepository;
import com.ecommerce.backend.service.interfaces.UserFavoriteService;

@Service
public class UserFavoriteServiceImpl implements UserFavoriteService {

    private final UserFavoriteRepository userFavoriteRepository;
    private final UserFavoriteMapper userFavoriteMapper;

    public UserFavoriteServiceImpl(UserFavoriteRepository userFavoriteRepository, UserFavoriteMapper userFavoriteMapper) {
        this.userFavoriteRepository = userFavoriteRepository;
        this.userFavoriteMapper = userFavoriteMapper;
    }

    @Override
    public List<UserFavoriteResponseDTO> findAll() {
        return userFavoriteRepository.findAll().stream()
                .map(userFavoriteMapper::toUserFavoriteResponseDTO)
                .toList();
    }

    @Override
    public Page<UserFavoriteResponseDTO> findAllPageable(Pageable pageable) {
        return userFavoriteRepository.findAll(pageable)
                .map(userFavoriteMapper::toUserFavoriteResponseDTO);
    }

    @Override
    public UserFavoriteResponseDTO findById(Long id) {
        return userFavoriteRepository.findById(id)
                .map(userFavoriteMapper::toUserFavoriteResponseDTO)
                .orElseThrow(() -> new RuntimeException("UserFavorite not found with id: " + id));
    }

    @Override
    public UserFavoriteResponseDTO createFromDto(UserFavoriteRequestDTO userFavoriteRequestDTO) {
        UserFavorite entity = new UserFavorite();
        userFavoriteMapper.updateEntityFromRequestDto(userFavoriteRequestDTO, entity);
        entity.setCreated_at(LocalDateTime.now());
        UserFavorite saved = userFavoriteRepository.save(entity);
        return userFavoriteMapper.toUserFavoriteResponseDTO(saved);
    }

    @Override
    public UserFavoriteResponseDTO updateFromDto(Long id, UserFavoriteRequestDTO userFavoriteRequestDTO) {
        UserFavorite entity = userFavoriteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserFavorite not found with id: " + id));
        userFavoriteMapper.updateEntityFromRequestDto(userFavoriteRequestDTO, entity);
        UserFavorite saved = userFavoriteRepository.save(entity);
        return userFavoriteMapper.toUserFavoriteResponseDTO(saved);
    }

    @Override
    public void deleteById(Long id) {
        if (!userFavoriteRepository.existsById(id)) {
            throw new RuntimeException("UserFavorite not found with id: " + id);
        }
        userFavoriteRepository.deleteById(id);
    }
}


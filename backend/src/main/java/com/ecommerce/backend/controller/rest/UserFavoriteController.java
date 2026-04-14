package com.ecommerce.backend.controller.rest;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.request.UserFavoriteRequestDTO;
import com.ecommerce.backend.dto.response.UserFavoriteResponseDTO;
import com.ecommerce.backend.service.interfaces.UserFavoriteService;

@RestController
@RequestMapping("/api/user-favorites")
public class UserFavoriteController extends BaseRestController<UserFavoriteResponseDTO, UserFavoriteRequestDTO, UserFavoriteRequestDTO> {

    public UserFavoriteController(UserFavoriteService userFavoriteService, PagedResourcesAssembler<UserFavoriteResponseDTO> pagedResourcesAssembler) {
        super(userFavoriteService, pagedResourcesAssembler);
    }
}

package com.ecommerce.backend.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.request.ShippingAddressRequestDTO;
import com.ecommerce.backend.dto.response.ShippingAddressResponseDTO;
import com.ecommerce.backend.mapper.ShippingAddressMapper;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.model.vo.ShippingAddress;
import com.ecommerce.backend.service.interfaces.UserService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/shipping-addresses")
public class ShippingAddressRestController {

    private final UserService userService;
    private final ShippingAddressMapper shippingAddressMapper;

    public ShippingAddressRestController(UserService userService, ShippingAddressMapper shippingAddressMapper) {
        this.userService = userService;
        this.shippingAddressMapper = shippingAddressMapper;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAddressesByUserId(@PathVariable Long userId) {
        try {
            User user = userService.getById(userId);
            List<ShippingAddress> list = user.getShippingAddresses();
            List<ShippingAddressResponseDTO> response = new ArrayList<>();
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    response.add(shippingAddressMapper.toResponseDTO(list.get(i), i, userId));
                }
            }
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createAddress(@RequestBody ShippingAddressRequestDTO request) {
        try {
            Long userId = request.userId();
            if (userId == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "User ID is required"));
            }
            User user = userService.getById(userId);
            List<ShippingAddress> list = user.getShippingAddresses();
            List<ShippingAddress> newList = list == null ? new ArrayList<>() : new ArrayList<>(list);
            
            ShippingAddress sa = shippingAddressMapper.toEntity(request);
            
            newList.add(sa);
            user.setShippingAddresses(newList);
            userService.save(user);
            
            int newId = newList.size() - 1;
            ShippingAddressResponseDTO response = shippingAddressMapper.toResponseDTO(sa, newId, userId);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(
            @PathVariable int id,
            @RequestBody ShippingAddressRequestDTO request) {
        try {
            Long userId = request.userId();
            if (userId == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "User ID is required"));
            }
            User user = userService.getById(userId);
            List<ShippingAddress> list = user.getShippingAddresses();
            if (id < 0 || id >= list.size()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Address index not found"));
            }
            
            ShippingAddress sa = list.get(id);
            shippingAddressMapper.updateEntityFromRequest(request, sa);
            
            user.setShippingAddresses(new ArrayList<>(list));
            userService.save(user);
            
            ShippingAddressResponseDTO response = shippingAddressMapper.toResponseDTO(sa, id, userId);
            
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(
            @PathVariable int id,
            @RequestParam Long userId) {
        try {
            if (userId == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "User ID is required"));
            }
            User user = userService.getById(userId);
            List<ShippingAddress> list = user.getShippingAddresses();
            if (id < 0 || id >= list.size()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Address index not found"));
            }
            
            list.remove(id);
            user.setShippingAddresses(new ArrayList<>(list));
            userService.save(user);
            
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

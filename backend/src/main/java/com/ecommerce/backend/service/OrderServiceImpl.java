package com.ecommerce.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.request.OrderRequestDTO;
import com.ecommerce.backend.dto.response.OrderResponseDTO;
import com.ecommerce.backend.mapper.OrderMapper;
import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.repository.OrderRepository;
import com.ecommerce.backend.service.interfaces.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderResponseDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderResponseDTO)
                .toList();
    }

    @Override
    public Page<OrderResponseDTO> findAllPageable(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toOrderResponseDTO);
    }

    @Override
    public OrderResponseDTO findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toOrderResponseDTO)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    public OrderResponseDTO createFromDto(OrderRequestDTO orderRequestDTO) {
        Order entity = new Order();
        orderMapper.updateEntityFromRequestDto(orderRequestDTO, entity);
        entity.setOrder_date(LocalDateTime.now());
        Order saved = orderRepository.save(entity);
        return orderMapper.toOrderResponseDTO(saved);
    }

    @Override
    public OrderResponseDTO updateFromDto(Long id, OrderRequestDTO orderRequestDTO) {
        Order entity = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderMapper.updateEntityFromRequestDto(orderRequestDTO, entity);
        Order saved = orderRepository.save(entity);
        return orderMapper.toOrderResponseDTO(saved);
    }

    @Override
    public void deleteById(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }
}


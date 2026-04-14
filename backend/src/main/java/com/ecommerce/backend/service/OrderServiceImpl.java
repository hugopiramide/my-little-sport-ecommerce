package com.ecommerce.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.request.OrderRequestDTO;
import com.ecommerce.backend.dto.response.OrderResponseDTO;
import com.ecommerce.backend.mapper.OrderMapper;
import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.repository.OrderRepository;
import com.ecommerce.backend.service.interfaces.OrderService;

@Service
public class OrderServiceImpl extends BaseCrudServiceImpl<Order, OrderResponseDTO, OrderRequestDTO, OrderRequestDTO> implements OrderService {

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        super(orderRepository);
        this.orderMapper = orderMapper;
    }

    @Override
    protected OrderResponseDTO toDto(Order entity) {
        return orderMapper.toOrderResponseDTO(entity);
    }

    @Override
    protected List<OrderResponseDTO> toDtoList(List<Order> entities) {
        return entities.stream().map(orderMapper::toOrderResponseDTO).toList();
    }

    @Override
    protected Order toEntity(OrderRequestDTO dto) {
        return orderMapper.toEntity(dto);
    }

    @Override
    protected void updateEntity(OrderRequestDTO dto, Order target) {
        orderMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected void updateEntityFromCreate(OrderRequestDTO dto, Order target) {
        orderMapper.updateEntityFromRequestDto(dto, target);
    }

    @Override
    protected Order newEntity() {
        return new Order();
    }

    @Override
    protected String getEntityName() {
        return "Order";
    }

    @Override
    protected void afterCreate(OrderRequestDTO dto, Order entity) {
        entity.setOrder_date(LocalDateTime.now());
    }
}

package com.ecommerce.backend.controller.rest;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.request.OrderRequestDTO;
import com.ecommerce.backend.dto.response.OrderResponseDTO;
import com.ecommerce.backend.service.interfaces.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController extends BaseRestController<OrderResponseDTO, OrderRequestDTO, OrderRequestDTO> {

    public OrderController(OrderService orderService, PagedResourcesAssembler<OrderResponseDTO> pagedResourcesAssembler) {
        super(orderService, pagedResourcesAssembler);
    }
}

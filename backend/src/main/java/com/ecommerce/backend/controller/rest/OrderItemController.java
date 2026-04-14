package com.ecommerce.backend.controller.rest;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.request.OrderItemRequestDTO;
import com.ecommerce.backend.dto.response.OrderItemResponseDTO;
import com.ecommerce.backend.service.interfaces.OrderItemService;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController extends BaseRestController<OrderItemResponseDTO, OrderItemRequestDTO, OrderItemRequestDTO> {

    public OrderItemController(OrderItemService orderItemService, PagedResourcesAssembler<OrderItemResponseDTO> pagedResourcesAssembler) {
        super(orderItemService, pagedResourcesAssembler);
    }
}

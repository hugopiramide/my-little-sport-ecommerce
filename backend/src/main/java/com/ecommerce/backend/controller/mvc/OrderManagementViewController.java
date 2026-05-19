package com.ecommerce.backend.controller.mvc;

import com.ecommerce.backend.dto.ShippingAddressDTO;
import com.ecommerce.backend.dto.request.OrderRequestDTO;
import com.ecommerce.backend.model.enums.OrderStatus;
import com.ecommerce.backend.service.interfaces.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Controller
@RequestMapping("/manage/orders")
public class OrderManagementViewController extends BaseManagementController {

    private final OrderService orderService;

    public OrderManagementViewController(OrderService orderService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public String list(Model model, 
                        @RequestParam(required = false) Long editId,
                        @RequestParam(required = false) String dateFrom,
                        @RequestParam(required = false) String dateTo,
                        @RequestParam(required = false) OrderStatus status,
                        @RequestParam(required = false, defaultValue = "0") int page,
                        @RequestParam(required = false, defaultValue = "10") int size) {
        model.addAttribute("entityName", "Orders");
        model.addAttribute("entityKey", "orders");
        model.addAttribute("editId", editId);
        
        String normalizedDateFrom = (dateFrom != null && !dateFrom.isBlank()) ? dateFrom : null;
        String normalizedDateTo = (dateTo != null && !dateTo.isBlank()) ? dateTo : null;
        
        boolean hasFilters = (normalizedDateFrom != null || normalizedDateTo != null || status != null);
        Page<?> pageResult;
        if (hasFilters) {
            var results = orderService.findByFilters(normalizedDateFrom, normalizedDateTo, status);
            int start = page * size;
            int end = Math.min(start + size, results.size());
            var pageContent = results.subList(Math.min(start, results.size()), end);
            pageResult = new PageImpl<>(pageContent, PageRequest.of(page, size), results.size());
            model.addAttribute("filterDateFrom", dateFrom);
            model.addAttribute("filterDateTo", dateTo);
            model.addAttribute("filterStatus", status);
            model.addAttribute("paginationPrevious", page > 0 ? buildPaginationUrl("/manage/orders/list", page - 1, size, paginationParams("dateFrom", dateFrom, "dateTo", dateTo, "status", status)) : null);
            model.addAttribute("paginationNext", page + 1 < pageResult.getTotalPages() ? buildPaginationUrl("/manage/orders/list", page + 1, size, paginationParams("dateFrom", dateFrom, "dateTo", dateTo, "status", status)) : null);
        } else {
            pageResult = orderService.findAllPageable(PageRequest.of(page, size));
            model.addAttribute("paginationPrevious", page > 0 ? buildPaginationUrl("/manage/orders/list", page - 1, size, null) : null);
            model.addAttribute("paginationNext", page + 1 < pageResult.getTotalPages() ? buildPaginationUrl("/manage/orders/list", page + 1, size, null) : null);
        }
        model.addAttribute("items", toMapList(pageResult.getContent()));
        model.addAttribute("currentPage", pageResult.getNumber());
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("pageSize", pageResult.getSize());
        
        model.addAttribute("allOrderStatuses", OrderStatus.values());
        return "management-list";
    }

    @PostMapping("/create")
    public String create(@RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            OrderRequestDTO dto = new OrderRequestDTO(
                    requiredLong(formData, "user_id"),
                    parseStatus(formData),
                    requiredDouble(formData, "total_price"),
                    new ShippingAddressDTO(
                        null, null, null, requiredText(formData, "shipping_addres"), null, null, null, null, null, null, null
                    )
            );
            Object result = orderService.createFromDto(dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "POST executed on orders");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in POST on orders");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#orders";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id, @RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            OrderRequestDTO dto = new OrderRequestDTO(
                    requiredLong(formData, "user_id"),
                    parseStatus(formData),
                    requiredDouble(formData, "total_price"),
                    new ShippingAddressDTO(
                        null, null, null, requiredText(formData, "shipping_addres"), null, null, null, null, null, null, null
                    )
            );
            Object result = orderService.updateFromDto(id, dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "PUT executed on orders");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in PUT on orders");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#orders";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            orderService.deleteById(id);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "DELETE executed on orders");
            redirectAttributes.addFlashAttribute("flashBody", "Operation completed with no content.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in DELETE on orders");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#orders";
    }

    private OrderStatus parseStatus(Map<String, String> formData) {
        return OrderStatus.valueOf(requiredText(formData, "status").trim().toUpperCase());
    }
}

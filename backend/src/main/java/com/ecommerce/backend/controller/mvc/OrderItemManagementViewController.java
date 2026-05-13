package com.ecommerce.backend.controller.mvc;

import com.ecommerce.backend.dto.request.OrderItemRequestDTO;
import com.ecommerce.backend.service.interfaces.OrderItemService;
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
@RequestMapping("/manage/order-items")
public class OrderItemManagementViewController extends BaseManagementController {

    private final OrderItemService orderItemService;

    public OrderItemManagementViewController(OrderItemService orderItemService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.orderItemService = orderItemService;
    }

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(required = false) Long editId,
                       @RequestParam(required = false) Long filterOrderId,
                       @RequestParam(required = false) String filterProductName,
                       @RequestParam(required = false, defaultValue = "0") int page,
                       @RequestParam(required = false, defaultValue = "10") int size) {
        model.addAttribute("entityName", "Order Items");
        model.addAttribute("entityKey", "order-items");
        model.addAttribute("editId", editId);
        model.addAttribute("filterOrderId", filterOrderId);
        model.addAttribute("filterProductName", filterProductName);

        Page<?> pageResult;
        boolean hasFilters = filterOrderId != null || (filterProductName != null && !filterProductName.trim().isEmpty());
        if (hasFilters) {
            var results = orderItemService.findByFilters(filterOrderId, filterProductName);
            int start = page * size;
            int end = Math.min(start + size, results.size());
            var pageContent = results.subList(Math.min(start, results.size()), end);
            pageResult = new PageImpl<>(pageContent, PageRequest.of(page, size), results.size());
            model.addAttribute("paginationPrevious", page > 0 ? buildPaginationUrl("/manage/order-items/list", page - 1, size, paginationParams("filterOrderId", filterOrderId, "filterProductName", filterProductName)) : null);
            model.addAttribute("paginationNext", page + 1 < pageResult.getTotalPages() ? buildPaginationUrl("/manage/order-items/list", page + 1, size, paginationParams("filterOrderId", filterOrderId, "filterProductName", filterProductName)) : null);
        } else {
            pageResult = orderItemService.findAllPageable(PageRequest.of(page, size));
            model.addAttribute("paginationPrevious", page > 0 ? buildPaginationUrl("/manage/order-items/list", page - 1, size, null) : null);
            model.addAttribute("paginationNext", page + 1 < pageResult.getTotalPages() ? buildPaginationUrl("/manage/order-items/list", page + 1, size, null) : null);
        }
        model.addAttribute("items", toMapList(pageResult.getContent()));
        model.addAttribute("currentPage", pageResult.getNumber());
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("pageSize", pageResult.getSize());
        return "management-list";
    }

    @PostMapping("/create")
    public String create(@RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            OrderItemRequestDTO dto = new OrderItemRequestDTO(
                    requiredLong(formData, "order_id"),
                    requiredLong(formData, "product_variant_id"),
                    requiredLong(formData, "quantity"),
                    requiredDouble(formData, "price_at_purchase")
            );
            Object result = orderItemService.createFromDto(dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "POST executed on order-items");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in POST on order-items");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#order-items";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id, @RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            OrderItemRequestDTO dto = new OrderItemRequestDTO(
                    requiredLong(formData, "order_id"),
                    requiredLong(formData, "product_variant_id"),
                    requiredLong(formData, "quantity"),
                    requiredDouble(formData, "price_at_purchase")
            );
            Object result = orderItemService.updateFromDto(id, dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "PUT executed on order-items");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in PUT on order-items");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#order-items";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            orderItemService.deleteById(id);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "DELETE executed on order-items");
            redirectAttributes.addFlashAttribute("flashBody", "Operation completed with no content.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in DELETE on order-items");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#order-items";
    }
}

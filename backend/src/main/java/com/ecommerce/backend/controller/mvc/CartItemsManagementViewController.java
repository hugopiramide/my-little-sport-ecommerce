package com.ecommerce.backend.controller.mvc;

import com.ecommerce.backend.dto.request.CartItemsRequestDTO;
import com.ecommerce.backend.service.interfaces.CartItemsService;

import tools.jackson.databind.ObjectMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/manage/cart-items")
public class CartItemsManagementViewController extends BaseManagementController {

    private final CartItemsService cartItemsService;

    public CartItemsManagementViewController(CartItemsService cartItemsService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.cartItemsService = cartItemsService;
    }

    @GetMapping("/list")
    public String list(Model model,
                        @RequestParam(required = false) Long editId,
                        @RequestParam(required = false, defaultValue = "0") int page,
                        @RequestParam(required = false, defaultValue = "8") int size) {
        Page<?> cartPage = cartItemsService.findAllPageable(PageRequest.of(page, size));
        model.addAttribute("entityName", "Cart Items");
        model.addAttribute("entityKey", "cart-items");
        model.addAttribute("editId", editId);
        model.addAttribute("items", toMapList(cartPage.getContent()));
        model.addAttribute("currentPage", cartPage.getNumber());
        model.addAttribute("totalPages", cartPage.getTotalPages());
        model.addAttribute("pageSize", cartPage.getSize());
        model.addAttribute("paginationPrevious", page > 0 ? buildPaginationUrl("/manage/cart-items/list", page - 1, size, null) : null);
        model.addAttribute("paginationNext", page + 1 < cartPage.getTotalPages() ? buildPaginationUrl("/manage/cart-items/list", page + 1, size, null) : null);
        return "management-list";
    }

    @PostMapping("/create")
    public String create(@RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            CartItemsRequestDTO dto = new CartItemsRequestDTO(
                    requiredLong(formData, "cart_id"),
                    requiredLong(formData, "product_variant_id"),
                    requiredLong(formData, "quantity")
            );
            Object result = cartItemsService.createFromDto(dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "POST executed on cart-items");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in POST on cart-items");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#cart-items";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id, @RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            CartItemsRequestDTO dto = new CartItemsRequestDTO(
                    requiredLong(formData, "cart_id"),
                    requiredLong(formData, "product_variant_id"),
                    requiredLong(formData, "quantity")
            );
            Object result = cartItemsService.updateFromDto(id, dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "PUT executed on cart-items");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in PUT on cart-items");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#cart-items";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            cartItemsService.deleteById(id);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "DELETE executed on cart-items");
            redirectAttributes.addFlashAttribute("flashBody", "Operation completed with no content.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in DELETE on cart-items");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#cart-items";
    }
}

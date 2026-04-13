package com.ecommerce.backend.controller.mvc;

import com.ecommerce.backend.dto.request.CartItemsRequestDTO;
import com.ecommerce.backend.service.interfaces.CartItemsService;
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
@RequestMapping("/manage/cart-items")
public class CartItemsManagementViewController extends BaseManagementController {

    private final CartItemsService cartItemsService;

    public CartItemsManagementViewController(CartItemsService cartItemsService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.cartItemsService = cartItemsService;
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(required = false) Long editId) {
        model.addAttribute("entityName", "Cart Items");
        model.addAttribute("entityKey", "cart-items");
        model.addAttribute("editId", editId);
        model.addAttribute("items", toMapList(cartItemsService.findAll()));
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
            redirectAttributes.addFlashAttribute("flashTitle", "POST ejecutado en cart-items");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error en POST sobre cart-items");
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
            redirectAttributes.addFlashAttribute("flashTitle", "PUT ejecutado en cart-items");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error en PUT sobre cart-items");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#cart-items";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            cartItemsService.deleteById(id);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "DELETE ejecutado en cart-items");
            redirectAttributes.addFlashAttribute("flashBody", "Operacion completada sin contenido.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error en DELETE sobre cart-items");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#cart-items";
    }
}

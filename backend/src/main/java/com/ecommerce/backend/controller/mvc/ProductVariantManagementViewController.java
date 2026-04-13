package com.ecommerce.backend.controller.mvc;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.backend.dto.request.ProductVariantRequestDTO;
import com.ecommerce.backend.service.interfaces.ProductVariantService;

import tools.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/manage/product-variants")
public class ProductVariantManagementViewController extends BaseManagementController {

    private final ProductVariantService productVariantService;

    public ProductVariantManagementViewController(ProductVariantService productVariantService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.productVariantService = productVariantService;
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(required = false) Long editId) {
        model.addAttribute("entityName", "Product Variants");
        model.addAttribute("entityKey", "product-variants");
        model.addAttribute("editId", editId);
        model.addAttribute("items", toMapList(productVariantService.findAll()));
        return "management-list";
    }

    @PostMapping("/create")
    public String create(@RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            ProductVariantRequestDTO dto = new ProductVariantRequestDTO(
                    requiredLong(formData, "product_id"),
                    requiredText(formData, "size"),
                    requiredLong(formData, "stock"),
                    requiredDouble(formData, "price_modifier")
            );
            Object result = productVariantService.createFromDto(dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "POST ejecutado en product-variants");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error en POST sobre product-variants");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#product-variants";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id, @RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Form Data: " + formData); // Debugging line
            ProductVariantRequestDTO dto = new ProductVariantRequestDTO(
                    requiredLong(formData, "productId"),
                    requiredText(formData, "size"),
                    requiredLong(formData, "stock"),
                    requiredDouble(formData, "priceModifier")
            );
            System.out.println("DTO Created: " + dto); // Debugging line
            Object result = productVariantService.updateFromDto(id, dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "PUT ejecutado en product-variants");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error en PUT sobre product-variants");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#product-variants";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            productVariantService.deleteById(id);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "DELETE ejecutado en product-variants");
            redirectAttributes.addFlashAttribute("flashBody", "Operacion completada sin contenido.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error en DELETE sobre product-variants");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#product-variants";
    }
}

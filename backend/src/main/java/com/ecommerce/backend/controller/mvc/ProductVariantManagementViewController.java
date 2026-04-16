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
import com.ecommerce.backend.service.interfaces.ProductService;
import com.ecommerce.backend.service.interfaces.ProductVariantService;

import tools.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/manage/product-variants")
public class ProductVariantManagementViewController extends BaseManagementController {

    private final ProductVariantService productVariantService;
    private final ProductService productService;

    public ProductVariantManagementViewController(ProductVariantService productVariantService, ProductService productService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.productVariantService = productVariantService;
        this.productService = productService;
    }

    @GetMapping("/list")
    public String list(Model model, 
                       @RequestParam(required = false) Long editId,
                       @RequestParam(required = false) Long productId,
                       @RequestParam(required = false) Long stockMin,
                       @RequestParam(required = false) Long stockMax,
                       @RequestParam(required = false) String size) {
        model.addAttribute("entityName", "Product Variants");
        model.addAttribute("entityKey", "product-variants");
        model.addAttribute("editId", editId);
        
        // Normalize empty string to null for proper filtering
        String normalizedSize = (size != null && !size.isBlank()) ? size : null;

        boolean hasFilters = (productId != null || stockMin != null || stockMax != null || normalizedSize != null);

        if (hasFilters) {
            model.addAttribute("items", toMapList(productVariantService.findByFilters(productId, stockMin, stockMax, normalizedSize)));
            model.addAttribute("filterProductId", productId);
            model.addAttribute("filterStockMin", stockMin);
            model.addAttribute("filterStockMax", stockMax);
            model.addAttribute("filterSize", normalizedSize);
        } else {
            model.addAttribute("items", toMapList(productVariantService.findAll()));
        }
        
        model.addAttribute("allProducts", productService.findAll());
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
            redirectAttributes.addFlashAttribute("flashTitle", "POST executed on product-variants");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in POST on product-variants");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#product-variants";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id, @RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            ProductVariantRequestDTO dto = new ProductVariantRequestDTO(
                    requiredLong(formData, "productId"),
                    requiredText(formData, "size"),
                    requiredLong(formData, "stock"),
                    requiredDouble(formData, "priceModifier")
            );
            Object result = productVariantService.updateFromDto(id, dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "PUT executed on product-variants");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in PUT on product-variants");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#product-variants";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            productVariantService.deleteById(id);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "DELETE executed on product-variants");
            redirectAttributes.addFlashAttribute("flashBody", "Operation completed with no content.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in DELETE on product-variants");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#product-variants";
    }
}

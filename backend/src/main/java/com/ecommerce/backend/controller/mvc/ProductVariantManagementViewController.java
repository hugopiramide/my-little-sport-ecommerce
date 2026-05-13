package com.ecommerce.backend.controller.mvc;

import java.util.Map;

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
                        @RequestParam(name = "filterSize", required = false) String filterSize,
                        @RequestParam(required = false, defaultValue = "0") int page,
                        @RequestParam(required = false, defaultValue = "8") int size) {
        model.addAttribute("entityName", "Product Variants");
        model.addAttribute("entityKey", "product-variants");
        model.addAttribute("editId", editId);
        
        String normalizedFilterSize = (filterSize != null && !filterSize.isBlank()) ? filterSize : null;

        boolean hasFilters = (productId != null || stockMin != null || stockMax != null || normalizedFilterSize != null);
        Page<?> variantPage;
        if (hasFilters) {
            var results = productVariantService.findByFilters(productId, stockMin, stockMax, normalizedFilterSize);
            int start = page * size;
            int end = Math.min(start + size, results.size());
            var pageContent = results.subList(Math.min(start, results.size()), end);
            variantPage = new PageImpl<>(pageContent, PageRequest.of(page, size), results.size());
            model.addAttribute("paginationPrevious", page > 0 ? buildPaginationUrl("/manage/product-variants/list", page - 1, size, paginationParams("productId", productId, "stockMin", stockMin, "stockMax", stockMax, "filterSize", normalizedFilterSize)) : null);
            model.addAttribute("paginationNext", page + 1 < variantPage.getTotalPages() ? buildPaginationUrl("/manage/product-variants/list", page + 1, size, paginationParams("productId", productId, "stockMin", stockMin, "stockMax", stockMax, "filterSize", normalizedFilterSize)) : null);
        } else {
            variantPage = productVariantService.findAllPageable(PageRequest.of(page, size));
            model.addAttribute("paginationPrevious", page > 0 ? buildPaginationUrl("/manage/product-variants/list", page - 1, size, null) : null);
            model.addAttribute("paginationNext", page + 1 < variantPage.getTotalPages() ? buildPaginationUrl("/manage/product-variants/list", page + 1, size, null) : null);
        }
        model.addAttribute("items", toMapList(variantPage.getContent()));
        model.addAttribute("currentPage", variantPage.getNumber());
        model.addAttribute("totalPages", variantPage.getTotalPages());
        model.addAttribute("pageSize", variantPage.getSize());

        model.addAttribute("filterProductId", productId);
        model.addAttribute("filterStockMin", stockMin);
        model.addAttribute("filterStockMax", stockMax);
        model.addAttribute("filterSize", normalizedFilterSize);
        
        model.addAttribute("allProducts", productService.findAll());
        return "management-list";
    }

    @PostMapping("/create")
    public String create(@RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            ProductVariantRequestDTO dto = new ProductVariantRequestDTO(
                    requiredLong(formData, "productId"),
                    requiredText(formData, "size"),
                    requiredLong(formData, "stock"),
                    requiredDouble(formData, "priceModifier")
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

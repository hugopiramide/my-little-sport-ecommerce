package com.ecommerce.backend.controller.mvc;

import com.ecommerce.backend.dto.request.ProductRequestDTO;
import com.ecommerce.backend.dto.response.ProductResponseDTO;
import com.ecommerce.backend.service.interfaces.CategoryService;
import com.ecommerce.backend.service.interfaces.ProductService;
import org.springframework.data.domain.Page;
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
@RequestMapping("/manage/products")
public class ProductManagementViewController extends BaseManagementController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductManagementViewController(ProductService productService, CategoryService categoryService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String list(Model model,
                        @RequestParam(required = false) Long editId,
                        @RequestParam(required = false) String search,
                        @RequestParam(required = false, defaultValue = "0") int page,
                        @RequestParam(required = false, defaultValue = "8") int size) {

        model.addAttribute("entityName", "Products");
        model.addAttribute("entityKey", "products");
        model.addAttribute("editId", editId);

        if (search != null && !search.isBlank()) {
            Page<ProductResponseDTO> productPage = productService.searchByNameDescription(search, PageRequest.of(page, size));
            model.addAttribute("items", toMapList(productPage.getContent()));
            model.addAttribute("searchQuery", search);
            model.addAttribute("currentPage", productPage.getNumber());
            model.addAttribute("totalPages", productPage.getTotalPages());
            model.addAttribute("pageSize", productPage.getSize());
            model.addAttribute("paginationPrevious", page > 0 ? buildPaginationUrl("/manage/products/list", page - 1, size, paginationParams("search", search)) : null);
            model.addAttribute("paginationNext", page + 1 < productPage.getTotalPages() ? buildPaginationUrl("/manage/products/list", page + 1, size, paginationParams("search", search)) : null);
        } else {
            Page<ProductResponseDTO> productPage = productService.findAllPageable(PageRequest.of(page, size));
            model.addAttribute("items", toMapList(productPage.getContent()));
            model.addAttribute("currentPage", productPage.getNumber());
            model.addAttribute("totalPages", productPage.getTotalPages());
            model.addAttribute("pageSize", productPage.getSize());
            model.addAttribute("paginationPrevious", page > 0 ? buildPaginationUrl("/manage/products/list", page - 1, size, null) : null);
            model.addAttribute("paginationNext", page + 1 < productPage.getTotalPages() ? buildPaginationUrl("/manage/products/list", page + 1, size, null) : null);
        }
        
        model.addAttribute("allCategories", categoryService.findAll());
        return "management-list";
    }

    @PostMapping("/create")  
    public String create(@RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            ProductRequestDTO dto = new ProductRequestDTO(
                    requiredText(formData, "name"),
                    optionalText(formData, "description"),
                    requiredDouble(formData, "basePrice"),
                    optionalText(formData, "imageUrl"),
                    checkbox(formData, "active"),
                    requiredInt(formData, "category_id")
            );
            Object result = productService.createFromDto(dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "POST executed on products");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in POST on products");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#products";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id, @RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            ProductRequestDTO dto = new ProductRequestDTO(
                    requiredText(formData, "name"),
                    optionalText(formData, "description"),
                    requiredDouble(formData, "basePrice"),
                    optionalText(formData, "imageUrl"),
                    checkbox(formData, "active"),
                    requiredInt(formData, "category_id")
            );
            Object result = productService.updateFromDto(id, dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "PUT executed on products");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in PUT on products");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#products";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "DELETE executed on products");
            redirectAttributes.addFlashAttribute("flashBody", "Operation completed with no content.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in DELETE on products");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#products";
    }
}

package com.ecommerce.backend.controller.mvc;

import com.ecommerce.backend.dto.request.ProductReviewRequestDTO;
import com.ecommerce.backend.model.enums.ReviewStatus;
import com.ecommerce.backend.service.interfaces.ProductReviewService;
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
@RequestMapping("/manage/product-reviews")
public class ProductReviewManagementViewController extends BaseManagementController {

    private final ProductReviewService productReviewService;

    public ProductReviewManagementViewController(ProductReviewService productReviewService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.productReviewService = productReviewService;
    }

    @GetMapping("/list")
    public String list(Model model,
                        @RequestParam(required = false) Long editId,
                        @RequestParam(required = false) Long filterProductId,
                        @RequestParam(required = false) ReviewStatus filterStatus,
                        @RequestParam(required = false, defaultValue = "0") int page,
                        @RequestParam(required = false, defaultValue = "8") int size) {

        model.addAttribute("entityName", "Product Reviews");
        model.addAttribute("entityKey", "product-reviews");
        model.addAttribute("editId", editId);
        model.addAttribute("allReviewStatuses", ReviewStatus.values());
        model.addAttribute("filterProductId", filterProductId);
        model.addAttribute("filterStatus", filterStatus);

        boolean hasFilters = (filterProductId != null || filterStatus != null);
        Page<?> pageResult;
        if (hasFilters) {
            var results = (filterProductId != null && filterStatus != null)
                    ? productReviewService.findByProductIdAndStatus(filterProductId, filterStatus)
                    : (filterProductId != null)
                    ? productReviewService.findByProductId(filterProductId)
                    : productReviewService.findAll().stream()
                        .filter(r -> {
                            @SuppressWarnings("unchecked")
                            Map<String, Object> map = objectMapper.convertValue(r, Map.class);
                            return filterStatus != null && filterStatus.name().equals(String.valueOf(map.get("status")));
                        })
                        .toList();
            int start = page * size;
            int end = Math.min(start + size, results.size());
            var pageContent = results.subList(Math.min(start, results.size()), end);
            pageResult = new PageImpl<>(pageContent, PageRequest.of(page, size), results.size());
            model.addAttribute("paginationPrevious", page > 0 ? buildPaginationUrl("/manage/product-reviews/list", page - 1, size, paginationParams("filterProductId", filterProductId, "filterStatus", filterStatus)) : null);
            model.addAttribute("paginationNext", page + 1 < pageResult.getTotalPages() ? buildPaginationUrl("/manage/product-reviews/list", page + 1, size, paginationParams("filterProductId", filterProductId, "filterStatus", filterStatus)) : null);
        } else {
            pageResult = productReviewService.findAllPageable(PageRequest.of(page, size));
            model.addAttribute("paginationPrevious", page > 0 ? buildPaginationUrl("/manage/product-reviews/list", page - 1, size, null) : null);
            model.addAttribute("paginationNext", page + 1 < pageResult.getTotalPages() ? buildPaginationUrl("/manage/product-reviews/list", page + 1, size, null) : null);
        }
        model.addAttribute("items", toMapList(pageResult.getContent()));
        model.addAttribute("currentPage", pageResult.getNumber());
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("pageSize", pageResult.getSize());

        return "management-list";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id,
                        @RequestParam Map<String, String> formData,
                        RedirectAttributes redirectAttributes) {
        try {
            ReviewStatus newStatus = ReviewStatus.valueOf(requiredText(formData, "status").trim().toUpperCase());
            ProductReviewRequestDTO dto =
                    new ProductReviewRequestDTO(
                            requiredLong(formData, "userId"),
                            requiredLong(formData, "orderId"),
                            requiredLong(formData, "productId"),
                            optionalText(formData, "title"),
                            optionalText(formData, "body"),
                            requiredInt(formData, "rating"),
                            newStatus
                    );
            Object result = productReviewService.updateFromDto(id, dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "PUT executed on product-reviews");
            redirectAttributes.addFlashAttribute("flashBody",
                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in PUT on product-reviews");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#product-reviews";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            productReviewService.deleteById(id);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "DELETE executed on product-reviews");
            redirectAttributes.addFlashAttribute("flashBody", "Operation completed with no content.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error in DELETE on product-reviews");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#product-reviews";
    }
}

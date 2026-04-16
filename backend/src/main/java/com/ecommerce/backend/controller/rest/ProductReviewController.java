package com.ecommerce.backend.controller.rest;

import java.util.List;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.request.ProductReviewRequestDTO;
import com.ecommerce.backend.dto.response.ProductReviewResponseDTO;
import com.ecommerce.backend.dto.response.ReviewSummaryResponseDTO;
import com.ecommerce.backend.model.enums.ReviewStatus;
import com.ecommerce.backend.service.interfaces.ProductReviewService;

@RestController
@RequestMapping("/api/reviews")
public class ProductReviewController
        extends BaseRestController<ProductReviewResponseDTO, ProductReviewRequestDTO, ProductReviewRequestDTO> {

    private final ProductReviewService productReviewService;

    public ProductReviewController(ProductReviewService productReviewService, PagedResourcesAssembler<ProductReviewResponseDTO> pagedResourcesAssembler) {
        super(productReviewService, pagedResourcesAssembler);
        this.productReviewService = productReviewService;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductReviewResponseDTO>> getByProduct(
            @PathVariable Long productId,
            @RequestParam(required = false) ReviewStatus status) {

        List<ProductReviewResponseDTO> result = (status != null)
                ? productReviewService.findByProductIdAndStatus(productId, status)
                : productReviewService.findByProductId(productId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/product/{productId}/summary")
    public ResponseEntity<ReviewSummaryResponseDTO> getSummary(@PathVariable Long productId) {
        return ResponseEntity.ok(productReviewService.getSummaryByProductId(productId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductReviewResponseDTO>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(productReviewService.findByUserId(userId));
    }

    @GetMapping("/approved")
    public ResponseEntity<List<ProductReviewResponseDTO>> getApproved() {
        return ResponseEntity.ok(productReviewService.findByStatus(ReviewStatus.APPROVED));
    }
}

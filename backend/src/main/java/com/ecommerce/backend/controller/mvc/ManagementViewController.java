package com.ecommerce.backend.controller.mvc;

import com.ecommerce.backend.service.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ManagementViewController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("entities", entities());
        model.addAttribute("allCategories", categoryService.findAll());
        return "index";
    }

    private List<EntityMenuItem> entities() {
        return List.of(
                new EntityMenuItem("categories", "Category", "/api/categories"),
                new EntityMenuItem("products", "Product", "/api/products"),
                new EntityMenuItem("product-variants", "ProductVariant", "/api/product-variants"),
                new EntityMenuItem("carts", "Cart", "/api/carts"),
                new EntityMenuItem("cart-items", "CartItems", "/api/cart-items"),
                new EntityMenuItem("orders", "Order", "/api/orders"),
                new EntityMenuItem("order-items", "OrderItem", "/api/order-items"),
                new EntityMenuItem("user-favorites", "UserFavorite", "/api/user-favorites"),
                new EntityMenuItem("users", "Users", "/manage/users/search")
        );
    }

    public record EntityMenuItem(String key, String title, String basePath) {
    }
}

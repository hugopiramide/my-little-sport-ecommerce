package com.ecommerce.backend.controller.mvc;

import com.ecommerce.backend.dto.request.OrderRequestDTO;
import com.ecommerce.backend.model.enums.OrderStatus;
import com.ecommerce.backend.service.interfaces.OrderService;
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
@RequestMapping("/manage/orders")
public class OrderManagementViewController extends BaseManagementController {

    private final OrderService orderService;

    public OrderManagementViewController(OrderService orderService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(required = false) Long editId) {
        model.addAttribute("entityName", "Orders");
        model.addAttribute("entityKey", "orders");
        model.addAttribute("editId", editId);
        model.addAttribute("items", toMapList(orderService.findAll()));
        return "management-list";
    }

    @PostMapping("/create")
    public String create(@RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            OrderRequestDTO dto = new OrderRequestDTO(
                    requiredLong(formData, "user_id"),
                    parseStatus(formData),
                    requiredDouble(formData, "total_price"),
                    requiredText(formData, "shipping_addres")
            );
            Object result = orderService.createFromDto(dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "POST ejecutado en orders");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error en POST sobre orders");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#orders";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id, @RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            OrderRequestDTO dto = new OrderRequestDTO(
                    requiredLong(formData, "user_id"),
                    parseStatus(formData),
                    requiredDouble(formData, "total_price"),
                    requiredText(formData, "shipping_addres")
            );
            Object result = orderService.updateFromDto(id, dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "PUT ejecutado en orders");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error en PUT sobre orders");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#orders";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            orderService.deleteById(id);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "DELETE ejecutado en orders");
            redirectAttributes.addFlashAttribute("flashBody", "Operacion completada sin contenido.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error en DELETE sobre orders");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#orders";
    }

    private OrderStatus parseStatus(Map<String, String> formData) {
        return OrderStatus.valueOf(requiredText(formData, "status").trim().toUpperCase());
    }
}

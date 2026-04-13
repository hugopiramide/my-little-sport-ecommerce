package com.ecommerce.backend.controller.mvc;

import com.ecommerce.backend.dto.request.UserFavoriteRequestDTO;
import com.ecommerce.backend.service.interfaces.UserFavoriteService;
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
@RequestMapping("/manage/user-favorites")
public class UserFavoriteManagementViewController extends BaseManagementController {

    private final UserFavoriteService userFavoriteService;

    public UserFavoriteManagementViewController(UserFavoriteService userFavoriteService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.userFavoriteService = userFavoriteService;
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(required = false) Long editId) {
        model.addAttribute("entityName", "User Favorites");
        model.addAttribute("entityKey", "user-favorites");
        model.addAttribute("editId", editId);
        model.addAttribute("items", toMapList(userFavoriteService.findAll()));
        return "management-list";
    }

    @PostMapping("/create")
    public String create(@RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            UserFavoriteRequestDTO dto = new UserFavoriteRequestDTO(
                    requiredLong(formData, "user_id"),
                    requiredLong(formData, "product_id"),
                    checkbox(formData, "notify_when_in_stock")
            );
            Object result = userFavoriteService.createFromDto(dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "POST ejecutado en user-favorites");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error en POST sobre user-favorites");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#user-favorites";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id, @RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            UserFavoriteRequestDTO dto = new UserFavoriteRequestDTO(
                    requiredLong(formData, "user_id"),
                    requiredLong(formData, "product_id"),
                    checkbox(formData, "notify_when_in_stock")
            );
            Object result = userFavoriteService.updateFromDto(id, dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "PUT ejecutado en user-favorites");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error en PUT sobre user-favorites");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#user-favorites";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            userFavoriteService.deleteById(id);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "DELETE ejecutado en user-favorites");
            redirectAttributes.addFlashAttribute("flashBody", "Operacion completada sin contenido.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error en DELETE sobre user-favorites");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#user-favorites";
    }
}

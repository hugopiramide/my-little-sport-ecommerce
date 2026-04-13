package com.ecommerce.backend.controller.mvc;

import com.ecommerce.backend.dto.request.CategoryRequestDTO;
import com.ecommerce.backend.service.interfaces.CategoryService;
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
@RequestMapping("/manage/categories")
public class CategoryManagementViewController extends BaseManagementController {

    private final CategoryService categoryService;

    public CategoryManagementViewController(CategoryService categoryService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(required = false) Long editId) {
        model.addAttribute("entityName", "Categories");
        model.addAttribute("entityKey", "categories");
        model.addAttribute("editId", editId);
        model.addAttribute("items", toMapList(categoryService.findAll()));
        return "management-list";
    }

    @PostMapping("/create")
    public String create(@RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            CategoryRequestDTO dto = new CategoryRequestDTO(
                    requiredText(formData, "name"),
                    optionalText(formData, "description")
            );
            Object result = categoryService.createFromDto(dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "POST ejecutado en categories");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error en POST sobre categories");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#categories";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id, @RequestParam Map<String, String> formData, RedirectAttributes redirectAttributes) {
        try {
            CategoryRequestDTO dto = new CategoryRequestDTO(
                    requiredText(formData, "name"),
                    optionalText(formData, "description")
            );
            Object result = categoryService.updateFromDto(id, dto);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "PUT ejecutado en categories");
            redirectAttributes.addFlashAttribute("flashBody", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error en PUT sobre categories");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#categories";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("flashType", "ok");
            redirectAttributes.addFlashAttribute("flashTitle", "DELETE ejecutado en categories");
            redirectAttributes.addFlashAttribute("flashBody", "Operacion completada sin contenido.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashTitle", "Error en DELETE sobre categories");
            redirectAttributes.addFlashAttribute("flashBody", ex.getMessage());
        }
        return "redirect:/#categories";
    }
}

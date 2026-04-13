package com.ecommerce.backend.controller.mvc;

import com.ecommerce.backend.model.User;
import com.ecommerce.backend.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tools.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/manage/users")
public class UserManagementViewController extends BaseManagementController {

    private final UserRepository userRepository;

    public UserManagementViewController(UserRepository userRepository, ObjectMapper objectMapper) {
        super(objectMapper);
        this.userRepository = userRepository;
    }

    @GetMapping("/search")
    public String searchUserByUsername(
            @RequestParam String username,
            RedirectAttributes redirectAttributes
    ) {
        try {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Debes indicar un nombre de usuario.");
            }

            User user = userRepository.findByPersonalDataUsername(username.trim())
                    .orElseThrow(() -> new IllegalArgumentException("No existe usuario con username: " + username));

            UserCardView userCard = new UserCardView(
                    user.getId(),
                    safeText(user.getPersonalData().getName()),
                    safeText(user.getPersonalData().getSurname()),
                    safeText(user.getPersonalData().getUsername()),
                    safeText(user.getPersonalData().getEmail()),
                    (user.getPersonalData().getBirthday() != null
                            && user.getPersonalData().getBirthday().getDate() != null)
                            ? user.getPersonalData().getBirthday().getDate().toString()
                            : "-",
                    safeText(user.getPersonalData().getProfileImgUrl()),
                    user.getRole() != null ? user.getRole().name() : "-"
            );

            redirectAttributes.addFlashAttribute("userSearchType", "ok");
            redirectAttributes.addFlashAttribute("userSearchTitle", "Usuario encontrado");
            redirectAttributes.addFlashAttribute("userSearchUser", userCard);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("userSearchType", "error");
            redirectAttributes.addFlashAttribute("userSearchTitle", "Error en busqueda de usuario");
            redirectAttributes.addFlashAttribute("userSearchError", ex.getMessage());
        }

        return "redirect:/#users";
    }

    public record UserCardView(
            Long id,
            String name,
            String surname,
            String username,
            String email,
            String birthday,
            String profileImgUrl,
            String role
    ) {
    }
}

package com.usermgmt.controller;

import com.usermgmt.entity.User;
import com.usermgmt.enums.Role;
import com.usermgmt.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String search,
                       @RequestParam(required = false) Role roleFilter,
                       @RequestParam(defaultValue = "firstName") String sort,
                       @RequestParam(defaultValue = "asc") String dir,
                       Model model) {
        Sort.Direction direction = "desc".equalsIgnoreCase(dir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sortObj = Sort.by(direction, sort);

        model.addAttribute("users", userService.search(search, roleFilter, sortObj));
        model.addAttribute("search", search != null ? search : "");
        model.addAttribute("roleFilter", roleFilter);
        model.addAttribute("roles", Role.values());
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        return "users/list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        model.addAttribute("pageTitle", "Add User");
        return "users/form";
    }

    @PostMapping("/new")
    public String createUser(@Valid @ModelAttribute("user") User user,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (userService.isEmailTakenByOther(user.getEmail(), null)) {
            result.rejectValue("email", "duplicate", "Email is already in use");
        }
        if (result.hasErrors()) {
            model.addAttribute("roles", Role.values());
            model.addAttribute("pageTitle", "Add User");
            return "users/form";
        }
        userService.save(user);
        redirectAttributes.addFlashAttribute("successMessage", "User created successfully.");
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("pageTitle", "Edit User");
                    return "users/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "User not found.");
                    return "redirect:/users";
                });
    }

    @PostMapping("/{id}/edit")
    public String updateUser(@PathVariable Long id,
                             @Valid @ModelAttribute("user") User user,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (!userService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found.");
            return "redirect:/users";
        }
        if (userService.isEmailTakenByOther(user.getEmail(), id)) {
            result.rejectValue("email", "duplicate", "Email is already in use");
        }
        if (result.hasErrors()) {
            model.addAttribute("roles", Role.values());
            model.addAttribute("pageTitle", "Edit User");
            return "users/form";
        }
        user.setId(id);
        userService.save(user);
        redirectAttributes.addFlashAttribute("successMessage", "User updated successfully.");
        return "redirect:/users";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!userService.findById(id).isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found.");
            return "redirect:/users";
        }
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
        return "redirect:/users";
    }
}

package com.walking.sellix.controller;

import com.walking.sellix.entity.Role;
import com.walking.sellix.model.user.UserDto;
import com.walking.sellix.model.user.UserFilter;
import com.walking.sellix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private static final List<Integer> PAGE_SIZES = List.of(10, 20, 40, 80, 100);

    private final UserService userService;

    @GetMapping
    public String index(UserFilter filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "40") int size,
                        Model model) {
        int validSize = PAGE_SIZES.contains(size) ? size : 40;

        Page<UserDto> usersPage = userService.getAll(filter, PageRequest.of(
                page, validSize,
                Sort.by(Sort.Direction.DESC, "createdAt")));

        model.addAttribute("filter", filter);
        model.addAttribute("roles", Role.values());
        model.addAttribute("usersPage", usersPage);
        model.addAttribute("currentSize", size);
        model.addAttribute("pageSizes", PAGE_SIZES);

        return "admin/admin-users";
    }

    @PatchMapping("/role/{id}")
    public String changeRole(@PathVariable("id") Long id) {
        userService.changeRole(id);

        return "redirect:/admin";
    }

    @PatchMapping("/status/{id}")
    public String changeStatus(@PathVariable("id") Long id) {
        userService.changeStatus(id);

        return "redirect:/admin";
    }
}

package com.walking.sellix.controller;

import com.walking.sellix.model.user.request.CreateUserRequest;
import com.walking.sellix.model.user.request.UpdateUserRequest;
import com.walking.sellix.service.UserService;
import com.walking.sellix.validator.UniqueUsernameValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UniqueUsernameValidator uniqueUsernameValidator;

    @PreAuthorize("@userAccessChecker.isOwner(#id, principal.id)")
    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        return userService.getById(id)
                .map(user -> {
                    model.addAttribute("user", user);

                    return "user/user-info";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/avatar")
    public ResponseEntity<byte[]> getAvatar(@PathVariable("id") Long id) {
        return userService.getAvatar(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping
    public String create(@ModelAttribute("userDto") @Validated CreateUserRequest userRequest,
                         BindingResult bindingResult) {
        uniqueUsernameValidator.validate(userRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        return Optional.of(userService.create(userRequest))
                .map(user -> "redirect:/login")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @PreAuthorize("@userAccessChecker.isOwner(#id, principal.id)")
    @GetMapping("/{id}/edit")
    public String updatePage(@PathVariable("id") Long id, Model model) {
        return userService.getById(id)
                .map(user -> {
                    UpdateUserRequest request = new UpdateUserRequest();

                    request.setId(id);
                    request.setFirstName(user.getFirstName());
                    request.setLastName(user.getLastName());
                    request.setPhoneNumber(user.getPhoneNumber());

                    model.addAttribute("avatar", user.getAvatar());
                    model.addAttribute("userDto", request);

                    return "user/user-edit";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("@userAccessChecker.isOwner(#id, principal.id)")
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("userDto") @Validated UpdateUserRequest userRequest,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            userService.getById(id)
                    .ifPresent(user ->
                            model.addAttribute("avatar", user.getAvatar()));

            return "user/user-edit";
        }

        return userService.update(id, userRequest)
                .map(user -> "redirect:/users/" + id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}

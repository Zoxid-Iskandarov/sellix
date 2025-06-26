package com.walking.sellix.controller;

import com.walking.sellix.model.announcement.AnnouncementFilter;
import com.walking.sellix.entity.CustomUserDetails;
import com.walking.sellix.model.announcement.AnnouncementDto;
import com.walking.sellix.model.announcement.request.CreateAnnouncementRequest;
import com.walking.sellix.model.announcement.request.UpdateAnnouncementRequest;
import com.walking.sellix.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {
    private static final List<String> CITIES = List.of(
            "Ташкент", "Самарканд", "Наманган", "Навои",
            "Каттакурган", "Кашкадарья", "Каракалпакстан",
            "Джизак", "Бухара", "Андижан"
    );

    private final AnnouncementService announcementService;

    @GetMapping
    public String index(AnnouncementFilter filter, Pageable pageable, Model model) {
        Page<AnnouncementDto> announcementPage = announcementService.getAll(filter,
                PageRequest.of(pageable.getPageNumber(), 30,
                Sort.by(Sort.Direction.DESC, "createdAt")));

        model.addAttribute("announcementPage", announcementPage);
        model.addAttribute("filter", filter);
        model.addAttribute("cities", CITIES);

        return "announcement/announcements";
    }

    @GetMapping("/{id}/preview")
    public ResponseEntity<?> preview(@PathVariable("id") Long announcementId) {
        return announcementService.getPreviewImage(announcementId)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public String showAnnouncement(@PathVariable Long id, Model model) {
        return announcementService.getById(id)
                .map(announcementReadDto -> {
                    model.addAttribute("announcementDto", announcementReadDto);
                    model.addAttribute("salesman", announcementReadDto.getSalesman());

                    return "announcement/announcement-info";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<?> images(@PathVariable("id") Long announcementImageId) {
        return announcementService.getImage(announcementImageId)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{id}")
    public String announcementUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("announcementDtos", announcementService.getByUserId(id));

        return "announcement/announcement-user";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("cities", CITIES);

        if (!model.containsAttribute("announcementRequest")) {
            model.addAttribute("announcementRequest", new CreateAnnouncementRequest());
        }

        return "announcement/announcement-create";
    }

    @PostMapping
    public String create(@ModelAttribute @Validated CreateAnnouncementRequest announcementRequest,
                         BindingResult bindingResult,
                         @AuthenticationPrincipal CustomUserDetails userDetails,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.announcementRequest", bindingResult);
            redirectAttributes.addFlashAttribute("announcementRequest", announcementRequest);

            return "redirect:/announcements/create";
        }

        announcementRequest.setUserId(userDetails.getId());
        announcementService.create(announcementRequest);

        return "redirect:/announcements";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") Long id, Model model) {
        return announcementService.getById(id)
                .map(announcementReadDto -> {
                    UpdateAnnouncementRequest request = new UpdateAnnouncementRequest();

                    request.setId(id);
                    request.setTitle(announcementReadDto.getTitle());
                    request.setDescription(announcementReadDto.getDescription());
                    request.setPrice(announcementReadDto.getPrice());
                    request.setCity(announcementReadDto.getCity());

                    model.addAttribute("announcementRequest", request);
                    model.addAttribute("images", announcementReadDto.getImages());
                    model.addAttribute("cities", CITIES);

                    return "announcement/announcement-edit";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("announcementRequest") @Validated UpdateAnnouncementRequest announcementRequest,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            announcementService.getById(id)
                    .ifPresent(announcementReadDto ->
                            model.addAttribute("images", announcementReadDto.getImages()));

            model.addAttribute("cities", CITIES);

            return "announcement/announcement-edit";
        }

        return announcementService.update(id, announcementRequest)
                .map(announcementDto -> "redirect:/announcements/" + id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}

package com.walking.sellix.service;

import com.walking.sellix.converter.announcement.AnnouncementDtoConverter;
import com.walking.sellix.converter.announcement.AnnouncementReadDtoConverter;
import com.walking.sellix.converter.announcement.request.CreateAnnouncementRequestConverter;
import com.walking.sellix.converter.announcement.request.UpdateAnnouncementRequestConverter;
import com.walking.sellix.entity.Announcement;
import com.walking.sellix.model.announcement.AnnouncementFilter;
import com.walking.sellix.entity.AnnouncementImage;
import com.walking.sellix.model.announcement.AnnouncementDto;
import com.walking.sellix.model.announcement.AnnouncementReadDto;
import com.walking.sellix.model.announcement.request.CreateAnnouncementRequest;
import com.walking.sellix.model.announcement.request.UpdateAnnouncementRequest;
import com.walking.sellix.repository.AnnouncementImageRepository;
import com.walking.sellix.repository.AnnouncementRepository;
import com.walking.sellix.repository.specification.AnnouncementSpecification;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementImageRepository announcementImageRepository;
    private final CreateAnnouncementRequestConverter createAnnouncementRequestConverter;
    private final AnnouncementDtoConverter announcementDtoConverter;
    private final AnnouncementReadDtoConverter announcementReadDtoConverter;
    private final UpdateAnnouncementRequestConverter updateAnnouncementRequestConverter;
    private final ImageService imageService;

    public Page<AnnouncementDto> getAll(AnnouncementFilter filter, Pageable pageable) {
        return announcementRepository.findAll(AnnouncementSpecification.filterAnnouncements(filter), pageable)
                .map(announcementDtoConverter::convert);
    }

    public List<AnnouncementDto> getByUserId(Long userId) {
        return announcementRepository.findAllByUserId(userId)
                .stream()
                .map(announcementDtoConverter::convert)
                .toList();
    }

    public Optional<byte[]> getPreviewImage(Long announcementId) {
        return announcementImageRepository.getPreviewImage(announcementId)
                .map(AnnouncementImage::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    public Optional<AnnouncementReadDto> getById(Long id) {
        return announcementRepository.findWithImagesById(id)
                .map(announcement -> {
                    Hibernate.initialize(announcement.getUser());
                    return announcementReadDtoConverter.convert(announcement);
                });
    }

    public Optional<byte[]> getImage(Long announcementImageId) {
        return announcementImageRepository.findById(announcementImageId)
                .map(AnnouncementImage::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public void create(CreateAnnouncementRequest announcementRequest) {
        Announcement announcement = createAnnouncementRequestConverter.convert(announcementRequest);
        uploadImages(announcement, announcementRequest.getImage1(),
                announcementRequest.getImage2(), announcementRequest.getImage3());

        announcementRepository.save(announcement);
    }

    @Transactional
    public Optional<AnnouncementDto> update(Long id, UpdateAnnouncementRequest request) {
        return announcementRepository.findById(id)
                .map(announcement -> {
                    if (hasNewImages(request.getImage1(), request.getImage2(), request.getImage3())) {
                        removeOldImages(announcement);
                        uploadImages(announcement, request.getImage1(), request.getImage2(), request.getImage3());
                    }

                    return updateAnnouncementRequestConverter.convert(request, announcement);
                })
                .map(announcementRepository::saveAndFlush)
                .map(announcementDtoConverter::convert);
    }

    @SneakyThrows
    private void uploadImages(Announcement announcement, MultipartFile... images) {
        boolean isFirst = true;

        for (MultipartFile image : images) {
            if (image == null || image.isEmpty()) {
                continue;
            }

            AnnouncementImage announcementImage = new AnnouncementImage();
            announcementImage.setImage(image.getOriginalFilename());
            announcementImage.setPreview(isFirst);
            isFirst = false;

            announcement.setImage(announcementImage);
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    private boolean hasNewImages(MultipartFile... images) {
        return Arrays.stream(images).anyMatch(image -> image != null && !image.isEmpty());
    }

    private void removeOldImages(Announcement announcement) {
        List<AnnouncementImage> toDelete = announcement.getImages();
        announcement.getImages().clear();

        announcementImageRepository.deleteAll(toDelete);
        announcementImageRepository.flush();
    }
}

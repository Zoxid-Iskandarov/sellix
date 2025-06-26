package com.walking.sellix.converter.announcement.request;

import com.walking.sellix.converter.AbstractConverter;
import com.walking.sellix.entity.Announcement;
import com.walking.sellix.model.announcement.request.UpdateAnnouncementRequest;
import org.springframework.stereotype.Component;

@Component
public class UpdateAnnouncementRequestConverter extends AbstractConverter<UpdateAnnouncementRequest, Announcement> {
    @Override
    public Announcement convert(UpdateAnnouncementRequest source, Announcement target) {
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setPrice(source.getPrice());
        target.setCity(source.getCity());

        return target;
    }
}

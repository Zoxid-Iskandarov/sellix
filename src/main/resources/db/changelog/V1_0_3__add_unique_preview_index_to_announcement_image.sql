CREATE UNIQUE INDEX unique_preview_per_announcement
    ON announcement_image (announcement_id) WHERE is_preview = true;
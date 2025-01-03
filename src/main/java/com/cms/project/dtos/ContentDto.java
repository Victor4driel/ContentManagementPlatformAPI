package com.cms.project.dtos;

import java.util.List;
import java.util.UUID;

public record ContentDto(
        String title,
        String body,
        String status,
        UUID userId,
        List<UUID> categoryIds,
        List<UUID> tagIds
) {}

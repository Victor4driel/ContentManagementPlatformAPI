package com.cms.project.services;

import com.cms.project.dtos.ContentDto;
import com.cms.project.models.Category;
import com.cms.project.models.Content;
import com.cms.project.models.Tag;
import com.cms.project.models.User;
import com.cms.project.repositories.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContentService {

    private ContentRepository contentRepository;

    private CategoryService categoryService;
    private TagService tagService;
    private UserService userService;

    @Autowired
    public ContentService(ContentRepository contentRepository, CategoryService categoryService, TagService tagService, UserService userService) {
        this.contentRepository = contentRepository;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.userService = userService;
    }

    public Content create(ContentDto contentDto) {
        List<Category> categories = null;
        List<Tag> tags = null;

        User author = userService.getById(contentDto.userId());

        if (contentDto.categoryIds() != null && !contentDto.categoryIds().isEmpty()) {
            categories = categoryService.findAllByIdIn(contentDto.categoryIds());
        }

        if (contentDto.tagIds() != null && !contentDto.tagIds().isEmpty()) {
            tags = tagService.findAllByIdIn(contentDto.tagIds());
        }

        Content content = new Content();

        content.setTitle(contentDto.title());
        content.setBody(contentDto.body());
        content.setTags(tags);
        content.setCategories(categories);
        content.setUser(author);

        return contentRepository.save(content);
    }

    public Iterable<Content> getAll(Integer pageNumber, Integer pageSize, String sort) {
        Sort sortOrder;

        if (sort.startsWith("-")){
            sortOrder = Sort.by(Sort.Direction.DESC, sort.substring(1));
        } else {
            sortOrder = Sort.by(Sort.Direction.ASC, sort);
        }

        Pageable page = PageRequest.of(pageNumber - 1, pageSize, sortOrder);

        return contentRepository.findAll(page);
    }

    public Content getById(UUID id) {
        return contentRepository.findById(id).orElse(null);
    }

    public Content update(UUID id, ContentDto contentDto) {
        Content content = getById(id);

        if (content == null) {
            return null;
        }

        if (contentDto.title() != null) {
            content.setTitle(contentDto.title());
        }

        if (contentDto.status() != null) {
            content.setStatus(contentDto.status());

            // somente usuário que tem a role como editor podem fazer essa alteração
        }

        if (contentDto.body() != null) {
            content.setBody(contentDto.body());
        }

        return contentRepository.save(content);
    }

    public Content addCategoriesAndTags(UUID id, ContentDto contentDto) {
        Content content = getById(id);

        if (content == null) {
            return null;
        }

        if (contentDto.categoryIds() != null && !contentDto.categoryIds().isEmpty()) {
            List<Category> newCategories = categoryService.findAllByIdIn(contentDto.categoryIds());
            content.getCategories().addAll(newCategories);
        }

        if (contentDto.tagIds() != null && !contentDto.tagIds().isEmpty()) {
            List<Tag> newTags = tagService.findAllByIdIn(contentDto.tagIds());
            content.getTags().addAll(newTags);
        }

        return contentRepository.save(content);
    }


    public void delete(UUID id) {
        contentRepository.deleteById(id);
    }
}

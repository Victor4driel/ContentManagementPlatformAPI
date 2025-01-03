package com.cms.project.controllers;

import com.cms.project.dtos.ContentDto;
import com.cms.project.models.Content;
import com.cms.project.services.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contents")
public class ContentController {

    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @PostMapping
    public ResponseEntity<Content> create(@RequestBody ContentDto contentDto) {
        Content content = contentService.create(contentDto);
        return new ResponseEntity<>(content, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Content>> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "title") String sort
    ) {
        Iterable<Content> contents = contentService.getAll(page, limit, sort);
        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> getById(@PathVariable UUID id) {
        Content content = contentService.getById(id);
        if (content == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Content> update(@PathVariable UUID id, @RequestBody ContentDto contentDto) {
        Content updatedContent = contentService.update(id, contentDto);
        if (updatedContent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedContent, HttpStatus.OK);
    }

    @PutMapping("/{id}/categories-tags")
    public ResponseEntity<Content> addCategoriesAndTags(
            @PathVariable UUID id,
            @RequestBody ContentDto contentDto
    ) {
        Content updatedContent = contentService.addCategoriesAndTags(id, contentDto);
        if (updatedContent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedContent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Content content = contentService.getById(id);
        if (content == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        contentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

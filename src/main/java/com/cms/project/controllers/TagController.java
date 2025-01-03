package com.cms.project.controllers;

import com.cms.project.dtos.TagDto;
import com.cms.project.models.Tag;
import com.cms.project.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<Tag> create(@RequestBody Tag tag) {
        Tag createdTag = tagService.create(tag);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Tag>> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "name") String sort
    ) {
        Iterable<Tag> tags = tagService.getAll(page, limit, sort);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getById(@PathVariable UUID id) {
        Tag tag = tagService.getById(id);
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PostMapping("/find-by-ids")
    public ResponseEntity<List<Tag>> findAllByIdIn(@RequestBody List<UUID> ids) {
        List<Tag> tags = tagService.findAllByIdIn(ids);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> update(@PathVariable UUID id, @RequestBody TagDto tagDto) {
        Tag updatedTag = tagService.update(id, tagDto);
        if (updatedTag == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedTag, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Tag tag = tagService.getById(id);
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

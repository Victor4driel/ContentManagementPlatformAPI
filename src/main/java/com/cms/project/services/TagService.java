package com.cms.project.services;

import com.cms.project.dtos.TagDto;
import com.cms.project.models.Tag;
import com.cms.project.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag create(Tag tag) {
        return tagRepository.save(tag);
    }

    public Iterable<Tag> getAll(Integer pageNumber, Integer pageSize, String sort) {
        Sort sortOrder;

        if (sort.startsWith("-")) {
            sortOrder = Sort.by(Sort.Direction.DESC, sort.substring(1));
        } else {
            sortOrder = Sort.by(Sort.Direction.ASC, sort);
        }

        Pageable page = PageRequest.of(pageNumber - 1, pageSize, sortOrder);

        return tagRepository.findAll(page);
    }

    public Tag getById(UUID id) {
        return tagRepository.findById(id).orElse(null);
    }

    public List<Tag> findAllByIdIn(List<UUID> ids) {
        return tagRepository.findAllByIdIn(ids);
    }

    public Tag update(UUID id, TagDto tagDto) {
        Tag tagToUpdate = getById(id);

        if (tagToUpdate == null) {
            return null;
        }

        if (tagDto.name() != null) {
            tagToUpdate.setName(tagDto.name());
        }

        if (tagDto.description() != null) {
            tagToUpdate.setDescription(tagDto.description());
        }

        return tagRepository.save(tagToUpdate);
    }

    public void delete(UUID id) {
        tagRepository.deleteById(id);
    }
}

package com.cms.project.services;

import com.cms.project.dtos.CategoryDto;
import com.cms.project.models.Category;
import com.cms.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.name(), categoryDto.description());

        return categoryRepository.save(category);
    }

    public Iterable<Category> getAll(Integer pageNumber, Integer pageSize, String sort) {
        Sort sortOrder;

        if (sort.startsWith("-")) {
            sortOrder = Sort.by(Sort.Direction.DESC, sort.substring(1));
        } else {
            sortOrder = Sort.by(Sort.Direction.ASC, sort);
        }

        Pageable pagination = PageRequest.of(pageNumber - 1, pageSize, sortOrder);

        return categoryRepository.findAll(pagination);
    }

    public Category getById(UUID id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> findAllByIdIn(List<UUID> ids) {
        return categoryRepository.findAllByIdIn(ids);
    }

    public Category update(UUID id, CategoryDto categoryDto) {
        Category category = getById(id);

        if (category == null) {
            return null;
        }

        if (categoryDto.name() != null) {
            category.setName(categoryDto.name());
        }

        if (categoryDto.description() != null) {
            category.setDescription(categoryDto.description());
        }

        return categoryRepository.save(category);
    }

    public void delete(UUID id) {
        categoryRepository.deleteById(id);
    }
}

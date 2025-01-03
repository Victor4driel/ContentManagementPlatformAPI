package com.cms.project.services;

import com.cms.project.dtos.UserDto;
import com.cms.project.models.User;
import com.cms.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(UserDto userDto) {
        User user = new User(userDto.name(), userDto.email(), userDto.role());

        return userRepository.save(user);
    }

    public Iterable<User> getAll(Integer pageNumber, Integer pageSize, String sort) {
        Sort sortOrder;

        if (sort.startsWith("-")) {
            sortOrder = Sort.by(Sort.Direction.DESC, sort.substring(1));
        } else {
            sortOrder = Sort.by(Sort.Direction.ASC, sort);
        }

        Pageable page = PageRequest.of(pageNumber - 1, pageSize, sortOrder);

        return userRepository.findAll(page);
    }

    public User getById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public User update(UUID id, UserDto userDto) {
        User userToUpdate = getById(id);

        if (userToUpdate == null) {
            return null;
        }

        if (userDto.name() != null) {
            userToUpdate.setName(userDto.name());
        }

        if (userDto.email() != null) {
            userToUpdate.setEmail(userDto.email());
        }

        if (userDto.role() != null) {
            userToUpdate.setRole(userDto.role());
        }

        return userRepository.save(userToUpdate);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}

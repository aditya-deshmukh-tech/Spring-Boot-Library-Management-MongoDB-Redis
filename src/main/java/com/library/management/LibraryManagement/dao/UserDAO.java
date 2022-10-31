package com.library.management.LibraryManagement.dao;

import com.library.management.LibraryManagement.repository.UserRepository;
import com.library.management.LibraryManagement.security.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class UserDAO {

    private UserRepository repository;

    @Cacheable(value = "user", key = "#username", unless = "#result != null")
    public UserClient getUser(String username) {
        return repository.findById(username).get();
    }

    public boolean existsById(String username) {
        return repository.existsById(username);
    }

    public void save(UserClient userClient) {
        repository.save(userClient);
    }

    public void update(UserClient userClient) {
        repository.save(userClient);
    }

    public void deleteUser(String username) {
        repository.deleteById(username);
    }

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }
}

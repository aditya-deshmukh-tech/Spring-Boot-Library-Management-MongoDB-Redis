package com.library.management.LibraryManagement.repository;

import com.library.management.LibraryManagement.security.UserClient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserClient, String> {
}

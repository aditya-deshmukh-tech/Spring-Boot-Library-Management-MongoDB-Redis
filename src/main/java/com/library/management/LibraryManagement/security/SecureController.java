package com.library.management.LibraryManagement.security;

import com.library.management.LibraryManagement.dao.UserDAO;
import com.library.management.LibraryManagement.exception.LibraryManagementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secure")
public class SecureController {

    private UserDAO userDAO;

    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody UserClient userClient) {
        if (userDAO.existsById(userClient.getUsername())) {
            throw new LibraryManagementException("user already exist..", "User alraedy exist..", 409);
        }
        userClient.setPassword(passwordEncoder.encode(userClient.getPassword()));
        userDAO.save(userClient);
        return ResponseEntity.ok("user created..");
    }

    @PutMapping("/update")
    public ResponseEntity<UserClient> updateClient(@RequestBody UserClient userClient) {
        if (!userDAO.existsById(userClient.getUsername())) {
            throw new LibraryManagementException("user not exist..", "User does not exist or deleted..", 400);
        }
        userClient.setPassword(passwordEncoder.encode(userClient.getPassword()));
        userDAO.update(userClient);
        return ResponseEntity.ok(userClient);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteClient(@PathVariable String username) {
        if (!userDAO.existsById(username)) {
            throw new LibraryManagementException("user not exist..", "User does not exist or deleted..", 400);
        }
        userDAO.deleteUser(username);
        return ResponseEntity.ok("user deleted..");
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}

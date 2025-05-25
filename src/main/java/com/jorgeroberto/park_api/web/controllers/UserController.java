package com.jorgeroberto.park_api.web.controllers;

import com.jorgeroberto.park_api.entities.User;
import com.jorgeroberto.park_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> create (@RequestBody User obj) {
        User user = userService.save(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById (@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updatePassword (@PathVariable Long id, @RequestBody User obj) {
        User user = userService.updatePassword(id, obj.getPassword());
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll () {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

}

package com.jorgeroberto.park_api.web.controllers;

import com.jorgeroberto.park_api.entities.User;
import com.jorgeroberto.park_api.services.UserService;
import com.jorgeroberto.park_api.web.dto.UserCreateDto;
import com.jorgeroberto.park_api.web.dto.UserPasswordDto;
import com.jorgeroberto.park_api.web.dto.UserResponseDto;
import com.jorgeroberto.park_api.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserResponseDto> create (@Valid @RequestBody UserCreateDto obj) {
        User user = userService.save(UserMapper.toUser(obj));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById (@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(UserMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updatePassword (@PathVariable Long id, @Valid @RequestBody UserPasswordDto dto) {
        User user = userService.updatePassword(id, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmNewPassword());
        //return ResponseEntity.ok(UserMapper.toDto(user));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll () {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(UserMapper.toListDto(users));
    }

}

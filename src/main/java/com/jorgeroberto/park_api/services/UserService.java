package com.jorgeroberto.park_api.services;

import com.jorgeroberto.park_api.entities.User;
import com.jorgeroberto.park_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User obj) {
        return userRepository.save(obj);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado!")
        );
    }

    @Transactional
    public User updatePassword(Long id, String password) {
        User user = findById(id);
        user.setPassword(password);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}

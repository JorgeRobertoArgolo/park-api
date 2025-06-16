package com.jorgeroberto.park_api.services;

import com.jorgeroberto.park_api.entities.User;
import com.jorgeroberto.park_api.exceptions.EntityNotFoundException;
import com.jorgeroberto.park_api.exceptions.PasswordInvalidException;
import com.jorgeroberto.park_api.exceptions.UsernameUniqueViolationException;
import com.jorgeroberto.park_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User obj) {

        try {
            obj.setPassword(passwordEncoder.encode(obj.getPassword())); //criptografia de senha
            return userRepository.save(obj);
        } catch (DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(String.format("Username {%s} already exists", obj.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id={%s} não encontrado!", id))
        );
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String confirmNewPassword) {

        if (!newPassword.equals(confirmNewPassword)) {
            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
        }

        User user = findById(id);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com 'username' não encontrado!", username))
        );
    }

    @Transactional(readOnly = true)
    public User.Role findRoleByUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }
}

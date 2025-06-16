package com.jorgeroberto.park_api.jwt;

import com.jorgeroberto.park_api.entities.User;
import com.jorgeroberto.park_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    //Consulta pelo username do user, retorna se for encontrado. Usuário logado
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        return new JwtUserDetails(user);
    }

    //Quando cliente for autenticar na aplicação
    public JwtToken getTokenAuthenticated(String username) {
        User.Role role = userService.findRoleByUsername(username);
        return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
    }
}

package ru.isshepelev.jwtrefresh.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.isshepelev.jwtrefresh.domain.Role;
import ru.isshepelev.jwtrefresh.domain.User;
import ru.isshepelev.jwtrefresh.repository.RoleRepository;
import ru.isshepelev.jwtrefresh.repository.UserRepository;
import ru.isshepelev.jwtrefresh.service.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.rmi.server.LogStream.log;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else log("User found " + username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log("saving User " + user.getName() + " " + user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log("saving role " + role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log("add role " + roleName + " in user " + username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log("getting user by username " + username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log("getting all users");
        return userRepository.findAll();
    }


}

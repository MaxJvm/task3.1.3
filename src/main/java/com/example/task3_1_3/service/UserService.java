package com.example.task3_1_3.service;

import com.example.task3_1_3.dao.UserRepository;
import com.example.task3_1_3.model.Role;
import com.example.task3_1_3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    private final UserRepository userRepository;

    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getRoles());
    }

    public Iterable<User> allUsers() {
        return userRepository.findAll();
    }

    public User findUserById(long id) {
        return userRepository.findById(id).get();
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void createUser(String name, String surname, String username, String password, long[] roles, int age) {
        User user = new User(username, passwordEncoder.encode(password), name, surname, age);
        setRoles(roles, user);
        save(user);
    }

    public void updateUser(String name, String surname, String username, String password, long[] roles, int age, long id) {
        User user = userRepository.findById(id).get();
        user.setPassword(passwordEncoder.encode(password));
        setRoles(roles, user);
        user.setUsername(username);
        user.setAge(age);
        user.setFirstName(name);
        user.setLastName(surname);
        userRepository.save(user);
    }

    private void setRoles(long[] roles, User user) {
        List<Role> roleList = new ArrayList<>();
        for (long n : roles) {
                roleList.add(roleService.findById(n));
        }
        user.setRoles(roleList);
    }
}

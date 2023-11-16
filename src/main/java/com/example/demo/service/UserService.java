package com.example.demo.service;

import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Plant;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetailsService;
import jakarta.servlet.http.HttpSession;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserserviceI{
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Override
    public User findByEmail(String email) {
       return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Not found user");
                });
    }

    @Override
    public void signin(LoginRequest userSign) {
        Optional<User> isExit =  userRepository.findByEmail(userSign.getEmail());
        Role role = roleRepository.findById(userSign.getRoleId()).orElse(null);
        System.out.println("role:" +role.getName());
        if(!isExit.isEmpty()){
            throw new UsernameNotFoundException("Đã tồn tại người dùng");
        }
        User user = new User();

        user.setFullName(userSign.getFullName());
        user.setEmail(userSign.getEmail());
        user.setPassword(encoder.encode(userSign.getPassword()));
        user.setRoles(List.of(role));
        user.setPhone(userSign.getPhone());

        userRepository.save(user);
    }

    @Override
    public User getUserLogin(HttpSession session) {
        String email = (String) session.getAttribute("MY_SESSION");
        return findByEmail(email);
    }

    @Override
    public List<User> getUsersByRoleName(String roleName) {
        List<User> userList = userRepository.findByRolesName(roleName);
        if(userList != null) return userList;
        return new ArrayList<>();
    }

    @Override
    public User findById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow( () -> {
                    throw new IllegalStateException("User not found");
                });

        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void updateUser(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());
            userRepository.save(existingUser);

        }
    }
}

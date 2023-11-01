package com.example.demo.controller.restController;

import com.example.demo.model.entity.User;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.service.UserserviceI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginHandle {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserserviceI userserviceI;

    @PostMapping("/login-handle")
    public ResponseEntity<?> loginHandle(@Validated @RequestBody LoginRequest userLogin, HttpServletRequest request, HttpSession session, HttpServletResponse response){

        String email = userLogin.getEmail();
        String password = userLogin.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                email,
                password
        );
        try {
            // Tiến hành xác thực
            Authentication authentication = authenticationManager.authenticate(token);
            // Lưu vào Context Holder
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Lưu vào trong session
            session.setAttribute("MY_SESSION", authentication.getName()); // Lưu maSV -> session
            SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);

            if (savedRequest ==null){
                return ResponseEntity.ok("/");
            }
            return ResponseEntity.ok(savedRequest.getRedirectUrl());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PostMapping("/signin-handle")
    public ResponseEntity<?> signinHanle(@Validated @RequestBody LoginRequest userLogin){
        try {
            System.out.println(userLogin.getEmail());
            userserviceI.signin(userLogin);
            return ResponseEntity.ok("Succ");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

}

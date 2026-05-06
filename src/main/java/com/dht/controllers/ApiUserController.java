package com.dht.controllers;

import com.dht.pojo.User;
import com.dht.services.UserService;
import com.dht.utils.JwtUtils;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserController {
    @Autowired
    private UserService userService;
    
    // Đăng ký User mới (Candidate hoặc Employer)
    @PostMapping(path = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<User> create(@RequestParam Map<String, String> info, 
                                     @RequestPart(value = "avatar", required = false) MultipartFile avatar) {
        User u = this.userService.addUser(info, avatar);
        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }
    
    // Đăng nhập trả về Token có kèm ROLE
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User u) {
        if (this.userService.authenticate(u.getUsername(), u.getPassword())) {
            try {
                User fullUser = this.userService.getUserByUsername(u.getUsername());
                // Truyền cả Username và Role vào Token
                String token = JwtUtils.generateToken(fullUser.getUsername(), fullUser.getRole());
                return ResponseEntity.ok().body(Collections.singletonMap("token", token));
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Lỗi tạo Token");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai tài khoản hoặc mật khẩu");
    }

    @GetMapping("/secure/profile")
    public ResponseEntity<User> getProfile(Principal principal) {
        return new ResponseEntity<>(this.userService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }
}
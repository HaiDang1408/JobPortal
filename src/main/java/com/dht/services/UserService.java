package com.dht.services;

import com.dht.pojo.User;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);
    User getUserById(int id); // Thêm để lấy profile
    User addUser(Map<String, String> info, MultipartFile avatar);
    boolean authenticate(String username, String password);
    boolean userExists(String username, String email); // Thêm để check trùng khi đăng ký
}
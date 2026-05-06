package com.dht.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dht.pojo.User;
import com.dht.repositories.UserRepository;
import com.dht.services.UserService;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("userDetailsService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public User getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }

    @Override
    public User getUserById(int id) {
        return this.userRepo.getUserById(id);
    }

    @Override
    public User addUser(Map<String, String> info, MultipartFile avatar) {
        User u = new User();
        u.setEmail(info.get("email"));
        u.setUsername(info.get("username"));
        u.setPassword(this.passwordEncoder.encode(info.get("password")));
        
        // Phân quyền: Mặc định là ứng viên nếu không truyền role
        u.setRole(info.getOrDefault("userRole", "ROLE_CANDIDATE"));
        
        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(avatar.getBytes(), 
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.userRepo.addUser(u);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepo.authenticate(username, password);
    }

    @Override
    public boolean userExists(String username, String email) {
        return this.userRepo.userExists(username, email);
    }

    // Phương thức bắt buộc của Spring Security UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Tài khoản không tồn tại!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getRole()));

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.controllers;

import com.dht.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author huu-thanhduong
 */
@Controller
@RequestMapping("/admin")
public class UserController {
    @GetMapping("/login")
    public String loginView() {
        return "login";
    }
    @GetMapping("/register")
    public String registerView(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Trỏ đến file register.html
    }

    @PostMapping("/register")
    public String registerProcess(@ModelAttribute("user") User user, 
                                  @RequestParam("confirmPassword") String confirm) {
        // 1. Kiểm tra mật khẩu khớp nhau
        // 2. Mã hóa mật khẩu (BCrypt)
        // 3. Lưu vào DB thông qua Service
        return "redirect:/login?success";
    }
}

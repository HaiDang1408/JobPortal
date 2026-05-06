/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.controllers;

import com.dht.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author phamd
 */
@ControllerAdvice // Dùng cái này để mọi Controller đều có categories
public class GlobalController {

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    public void commonAttributes(Model model) {
        // Đẩy danh sách ngành nghề vào Model để trang base.html lúc nào cũng có
        model.addAttribute("categories", this.categoryService.getCates());
    }
}
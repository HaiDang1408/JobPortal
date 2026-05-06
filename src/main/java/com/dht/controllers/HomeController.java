package com.dht.controllers;

import com.dht.services.CategoryService;
import com.dht.services.JobService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@ControllerAdvice
public class HomeController {
    @Autowired
    private CategoryService cateService;
    @Autowired
    private JobService jobService;
    
    @ModelAttribute
    public void commonResponse(Model model) {
        model.addAttribute("categories", this.cateService.getCates());
    }
    
    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("jobs", this.jobService.getJobs(params));
        return "index";
    }
}
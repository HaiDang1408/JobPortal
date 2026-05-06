package com.dht.controllers;

import com.dht.pojo.Job;
import com.dht.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Gemini_AI_Refactor
 */
@Controller
@RequestMapping("/admin")
public class JobController {
    @Autowired
    private JobService jobService;
    
    // Trang hiển thị form thêm mới công việc (Admin có thể đăng tin mẫu)
    @GetMapping("/jobs")
    public String createView(Model model) {
        model.addAttribute("job", new Job());
        return "jobs"; // Trả về view jobs.jsp
    }
    
    // Xử lý lưu hoặc cập nhật công việc
    @PostMapping("/jobs")
    public String create(Model model, @ModelAttribute(value = "job") Job j) {
        try {
            this.jobService.addOrUpdateJob(j);
            return "redirect:/"; // Sau khi lưu xong về trang chủ
        } catch (Exception ex) {
            model.addAttribute("err", ex.getMessage());
            return "jobs";
        }
    }
    
    // Trang chỉnh sửa thông tin công việc cụ thể
    @GetMapping("/jobs/{jobId}")
    public String updateView(@PathVariable(value = "jobId") int id, Model model) {
        model.addAttribute("job", this.jobService.getJobById(id));
        return "jobs";
    }
}
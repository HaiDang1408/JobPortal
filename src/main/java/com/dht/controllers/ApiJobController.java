package com.dht.controllers;

import com.dht.pojo.Job;
import com.dht.services.JobService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiJobController {
    @Autowired
    private JobService jobService;
    
    // Lấy danh sách việc làm (tìm kiếm, lọc, phân trang)
    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> list(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.jobService.getJobs(params), HttpStatus.OK);
    }
    
    // Xem chi tiết một công việc
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<Job> retrieve(@PathVariable(value = "jobId") int id) {
        return new ResponseEntity<>(this.jobService.getJobById(id), HttpStatus.OK);
    }

    // Xóa tin tuyển dụng (Chỉ Admin hoặc Employer sở hữu tin đó)
    @DeleteMapping("/jobs/{jobId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYER')")
    public void destroy(@PathVariable(value = "jobId") int id) {
        this.jobService.deleteJob(id);
    }
}
package com.dht.controllers;

import com.dht.pojo.Application;
import com.dht.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiApplicationController {
    @Autowired
    private ApplicationService appService;
    
    // Nộp đơn ứng tuyển (Có kèm lời nhắn và file CV)
    @PostMapping(path = "/secure/apply", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Application> create(@RequestParam Map<String, String> params, 
                                            @RequestPart MultipartFile cvFile) {
        Application app = this.appService.addApplication(params, cvFile);
        return new ResponseEntity<>(app, HttpStatus.CREATED);
    }
}
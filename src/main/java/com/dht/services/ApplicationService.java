package com.dht.services;

import com.dht.pojo.Application;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface ApplicationService {
    // Xử lý nộp đơn kèm file CV
    Application addApplication(Map<String, String> params, MultipartFile cvFile);
    List<Application> getApplicationsByJob(int jobId);
    List<Application> getApplicationsByUser(int userId);
}
package com.dht.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dht.pojo.Application;
import com.dht.pojo.Job;
import com.dht.pojo.User;
import com.dht.repositories.ApplicationRepository;
import com.dht.repositories.JobRepository;
import com.dht.repositories.UserRepository;
import com.dht.services.ApplicationService;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository appRepo;
    @Autowired
    private JobRepository jobRepo; // Thêm Repo này để tìm Job
    @Autowired
    private UserRepository userRepo; // Thêm Repo này để tìm User
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Application addApplication(Map<String, String> params, MultipartFile cvFile) {
        Application app = new Application();
        
        // 1. Gán nội dung và thời gian
        app.setContent(params.get("content"));
        app.setCreatedAt(new Date());
        app.setStatus("PENDING");

        // 2. Tìm Object Job từ ID và gán vào Application
        if (params.containsKey("jobId")) {
            int jobId = Integer.parseInt(params.get("jobId"));
            Job j = this.jobRepo.getJobById(jobId);
            app.setJobId(j); // Giả sử hàm setter là setJobId(Job job)
        }

        // 3. Tìm Object User từ ID và gán vào Application
        if (params.containsKey("candidateId")) {
            int candidateId = Integer.parseInt(params.get("candidateId"));
            User u = this.userRepo.getUserById(candidateId);
            app.setCandidateId(u); // Giả sử hàm setter là setCandidateId(User user)
        }

        // 4. Xử lý upload file CV lên Cloudinary
        if (cvFile != null && !cvFile.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(cvFile.getBytes(), 
                        ObjectUtils.asMap("resource_type", "auto"));
                app.setCvUrl(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ApplicationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return this.appRepo.addApplication(app);
    }

    @Override
    public List<Application> getApplicationsByJob(int jobId) {
        return this.appRepo.getApplicationsByJob(jobId);
    }

    @Override
    public List<Application> getApplicationsByUser(int userId) {
        return this.appRepo.getApplicationsByUser(userId);
    }
}
package com.dht.services;

import com.dht.pojo.Job;
import java.util.List;
import java.util.Map;

public interface JobService {
    List<Job> getJobs(Map<String, String> params);
    void addOrUpdateJob(Job j);
    Job getJobById(int id);
    void deleteJob(int id);
    Long countJobs(); // Thêm để hỗ trợ phân trang
}
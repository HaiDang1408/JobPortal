package com.dht.services.impl;

import com.dht.pojo.Job;
import com.dht.repositories.JobRepository;
import com.dht.services.JobService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepo;

    @Override
    public List<Job> getJobs(Map<String, String> params) {
        return this.jobRepo.getJobs(params);
    }

    @Override
    public void addOrUpdateJob(Job j) {
        this.jobRepo.addOrUpdateJob(j);
    }

    @Override
    public Job getJobById(int id) {
        return this.jobRepo.getJobById(id);
    }

    @Override
    public void deleteJob(int id) {
        this.jobRepo.deleteJob(id);
    }

    @Override
    public Long countJobs() {
        return this.jobRepo.countJobs();
    }
}
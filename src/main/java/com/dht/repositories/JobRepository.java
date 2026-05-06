/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.repositories;

import com.dht.pojo.Job;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface JobRepository {
    List<Job> getJobs(Map<String, String> params); // Lấy danh sách việc làm (lọc theo lương, địa điểm, từ khóa)
    void addOrUpdateJob(Job j);                   // Đăng tin hoặc sửa tin tuyển dụng
    Job getJobById(int id);                        // Xem chi tiết công việc
    void deleteJob(int id);                        // Xóa tin tuyển dụng
    Long countJobs();                              // (Thêm) Thống kê số lượng tin để làm phân trang
}

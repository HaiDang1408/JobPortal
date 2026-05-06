/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.repositories;

import com.dht.pojo.Application;
import java.util.List;

/**
 *
 * @author phamd
 */
public interface ApplicationRepository {
    Application addApplication(Application app);           // Nộp đơn ứng tuyển
    List<Application> getApplicationsByJob(int jobId); // Nhà tuyển dụng xem danh sách người nộp
    List<Application> getApplicationsByUser(int userId);
}

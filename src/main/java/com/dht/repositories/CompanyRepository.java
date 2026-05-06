/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dht.repositories;

import com.dht.pojo.Company;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phamd
 */
public interface CompanyRepository {
    Company getCompanyById(int id);
    void addOrUpdateCompany(Company c);
    List<Company> getCompanies(Map<String, String> params);
}

package com.excel.excel.Service;

import com.excel.excel.Entity.Employee;
import com.excel.excel.Repository.EmployeeAddressRepository;
import com.excel.excel.Repository.EmployeeDetailsRepository;
import com.excel.excel.Repository.EmployeeRepsoitory;
import com.excel.excel.util.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.util.List;

import org.slf4j.LoggerFactory;
import  org.slf4j.Logger;

@Service

public class EmployeeService {
    private static final Logger logger=LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    private EmployeeRepsoitory employeeRepository;

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    @Autowired
    private EmployeeAddressRepository employeeAddressRepository;

    public void saveUsersFromExcel(MultipartFile file) {
        logger.info("Process is started");
        try {
            List<Employee> employees = ExcelHelper.excelToDatabase(file.getInputStream());
            for (Employee user : employees) {
                if (!employeeRepository.existsByemployeeEmail(user.getEmployeeEmail())) {
                    logger.info("Saving employee with email:{}",user.getEmployeeEmail());
                    employeeRepository.save(user);
                    employeeDetailsRepository.save(user.getEmployeeDetails());
                    employeeAddressRepository.save(user.getEmployeeAddress());
                }
                else{
                    logger.warn("duplicate employee with:{}",user.getEmployeeEmail());
                }
            }
            logger.info("process is finished");
        }catch (IOException e){
            logger.error("failed");
            throw new RuntimeException("Failed to store excel data: " + e.getMessage());
        }
        catch (Exception e) {
            logger.error("error");
            throw new RuntimeException("Failed to store excel data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream loadEmployeeToExcel() {
        List<Employee> employees = employeeRepository.findAll();
        return ExcelHelper.usersToExcel(employees);
    }
}
package com.excel.excel.Repository;

import com.excel.excel.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepsoitory extends JpaRepository<Employee,Long> {
    boolean existsByemployeeEmail(String employeeEmail);

}

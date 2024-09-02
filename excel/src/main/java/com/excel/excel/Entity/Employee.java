package com.excel.excel.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Getter
@Setter
@Entity
@Table(name="Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  int id;
    private String employeeName;
    private String employeeEmail;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private EmployeeDetails employeeDetails;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private EmployeeAddress employeeAddress;
}

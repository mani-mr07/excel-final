package com.excel.excel.Controller;

import com.excel.excel.Service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/excel")
    public ResponseEntity<?>generateExcel(HttpServletResponse response)throws  Exception{
        ByteArrayInputStream bs=employeeService.loadEmployeeToExcel();

        String currentTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName="Employee"+currentTime+".xlsx";

        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+fileName).
                contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).
                body(new InputStreamResource(bs));
    }
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "File is empty";
        }

        try {
            employeeService.saveUsersFromExcel(file);
            return "File uploaded and data saved successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload and save data";
        }
    }
}

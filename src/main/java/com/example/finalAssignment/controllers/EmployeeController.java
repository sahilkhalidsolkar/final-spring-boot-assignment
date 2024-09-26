package com.example.finalAssignment.controllers;

import com.example.finalAssignment.dtos.EmployWeatherDto;
import com.example.finalAssignment.dtos.Weather;
import com.example.finalAssignment.models.Employee;
import com.example.finalAssignment.services.EmployeeService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    @PostMapping
    public Employee addEmployee(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("location") String location,
            @RequestParam("file") MultipartFile file
    ) {

        Employee employee = new Employee();
        employee.setName(name);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setLocation(location);
        System.out.println(employee);
        return employeeService.addEmployee(employee, file);


    }

    @GetMapping("/dbtocsv")
    public String exportToCsv() {
        employeeService.writeEmployeeToCSVFile("Employee.csv");
        return "successfully exported";
    }

    @GetMapping("/csvtodb")
    public String importFromCsv() {
        employeeService.importFromCsvToDb("Employee.csv");
        return "successfully imported from csv to db";
    }

    @GetMapping({"/weather/{id}"})
    public EmployWeatherDto getEmployeeWeather(@PathVariable("id")Integer id){

        return employeeService.getEmployeeWithWeather(id);

    }

}

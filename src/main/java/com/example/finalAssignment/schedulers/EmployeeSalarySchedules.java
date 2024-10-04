package com.example.finalAssignment.schedulers;

import com.example.finalAssignment.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@AllArgsConstructor
public class EmployeeSalarySchedules {
    private EmployeeService employeeService;

    @Scheduled(cron = "* * * L * *")
    public void scheduleSalaryCreditedEmail(){
        System.out.println("Email Scheduled to be sent");
        employeeService.sendEmail();
    }
}

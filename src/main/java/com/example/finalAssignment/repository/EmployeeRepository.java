package com.example.finalAssignment.repository;

import com.example.finalAssignment.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}

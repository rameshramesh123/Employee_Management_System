package com.example.Employee_Management_System_2.Repository;

import com.example.Employee_Management_System_2.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByEmail(String email);
}

package com.example.Employee_Management_System_2.Controller;
import com.example.Employee_Management_System_2.Entity.Employee;
import com.example.Employee_Management_System_2.Service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/home")
    public String homePage() {
        return "index";
    }

    @GetMapping("/employeesList")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employee-list";
    }

    @GetMapping("/view/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "employee-details";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form";
    }
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee, BindingResult result, Model model) {
        Employee existingEmailUser = employeeService.findingUserByEmail(employee.getEmail());

        if (existingEmailUser != null) {
            result.rejectValue("email", "email.exists", "This email is already in use!");
            // Use a message key
        }
        if (result.hasErrors()) {
            model.addAttribute("employee", employee);
            return "employee-form";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/employees/employeesList";
    }

    @GetMapping("/edit/{id}")
    public String updateEmployee(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "employee-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees/employeesList";
    }
}


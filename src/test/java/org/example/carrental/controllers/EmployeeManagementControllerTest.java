package org.example.carrental.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.carrental.Service.EmployeeService;
import org.example.carrental.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeManagementController.class)
class EmployeeManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Mock
    private Model model;

    @InjectMocks
    private EmployeeManagementController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void opretPersonale() throws Exception {
        Employee employee = new Employee();
        employee.setFull_name("John Doe");
        employee.setEmail("john.doe@gmail.com");

        mockMvc.perform(post("/opretPersonale")
                        .param("name", employee.getFull_name())
                        .param("role", employee.getEmail()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/personale"));

        verify(employeeService).createEmployee(employee);
    }
}
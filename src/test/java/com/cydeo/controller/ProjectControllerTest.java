package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Gender;
import com.cydeo.enums.Status;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    //controller bean
    //service bean
    //repository bean   , for these @SpringBootTest and @AutoConfigureMockMvc

    @Autowired
    private MockMvc mvc;

    //before all method, initially
    static UserDTO manager;
    static ProjectDTO project;

    @BeforeAll
    static void setUp(){

        manager = new UserDTO(2l,
                "",
                "","ozzy","abc1",
                "",true,"",
                new RoleDTO(2L,"Manager"), Gender.MALE);



        project = new ProjectDTO(
                "API Project",
                "PR001",
                manager,
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                "Some details",
                Status.OPEN
        );

    }


}
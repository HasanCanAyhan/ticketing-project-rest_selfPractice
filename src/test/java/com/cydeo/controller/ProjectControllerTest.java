package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Gender;
import com.cydeo.enums.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    //controller bean
    //service bean
    //repository bean   , for these @SpringBootTest and @AutoConfigureMockMvc

    @Autowired
    private MockMvc mvc;

    static String token;


    //before all method, initially
    static UserDTO manager;
    static ProjectDTO project;

    @BeforeAll
    static void setUp(){

        token = "Bearer " + "" +
                "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJMVF9mdUlidVJwWWkzSFliaUJtZWl6SzZoRGVQLXM1OGZrdWNXeGt3ZVNJIn0.eyJleHAiOjE2NzE0MDk2MzYsImlhdCI6MTY3MTM5MTYzNiwianRpIjoiOWE5MmJmNzItZDI3My00OTE1LTlmMDgtZWZkMDAzODNhZDQxIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2N5ZGVvLWRldiIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJkNTU0NjgxNy03YTYzLTRjYTQtODQwNC1mOTRmZmRjZDVlMDUiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ0aWNrZXRpbmctYXBwIiwic2Vzc2lvbl9zdGF0ZSI6IjdlNGUyZDA5LTQ0MzQtNDA1MC05NTQyLWJiYWM2YWE0MWYzNCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo4MDgxIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsImRlZmF1bHQtcm9sZXMtY3lkZW8tZGV2IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJ0aWNrZXRpbmctYXBwIjp7InJvbGVzIjpbIk1hbmFnZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiI3ZTRlMmQwOS00NDM0LTQwNTAtOTU0Mi1iYmFjNmFhNDFmMzQiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwicHJlZmVycmVkX3VzZXJuYW1lIjoib3p6eSJ9.enRsNn-mBEl1DazunYtm1EP07fWCZI3QnN6iMWCoXl4dQQ5tu-scqaWMC7swNCxVHaIGU_w1OHs_SCRSsVhHbRSmSQxeNQsFIDPD625UmYHzZNM9zHdUdmY2EKwhTTMtGzbEqJ1QXdbv_PWJqPVOzzPjtg8HqaDiqohpM5UL_GcBVxtAiOiT1_l3ysz2lRlC4zDKs-VjeYYx8XvNVLw4HiL5gg9XtZiuwQ4dNiS0DjHjeFZr__4WftdHTPbZJadAb28y72ikVsaIWhJHXgDkH0eMofc1uI5EvhPmvi3GckKO8QhFUkeRvu6piG2QZgZFlMe0ZRagejCkIBczKIYeJg";

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


    @Test
    void givenNoToken_getProjects() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/project"))
                .andExpect(status().is4xxClientError());//bcs we are not give access token
    }

    @Test
    void givenToken_getProjects() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/project")
                .header("Authorization",token)//we extend the time of the token in the keycloak for 5 hours
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].projectCode").exists())
                .andExpect(jsonPath("$.data[0].assignedManager.userName").exists()) //checking field of userName
                .andExpect(jsonPath("$.data[0].assignedManager.userName").isNotEmpty()) // checking value of username is not empty
                .andExpect(jsonPath("$.data[0].assignedManager.userName").value("ozzy"));

    }




}
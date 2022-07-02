package com.se2project.dream.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se2project.dream.entity.Agronomist;
import com.se2project.dream.extraClasses.Response;
import com.se2project.dream.entity.Farmer;
import com.se2project.dream.service.AgronomistService;
import com.se2project.dream.service.FarmerService;

import com.se2project.dream.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
public class LoginController {


    private final FarmerService farmerService;
    private final AgronomistService agronomistService;
    private final LoginService loginService;


    public LoginController(FarmerService farmerService, AgronomistService agronomistService, LoginService loginService) {
        this.farmerService = farmerService;
        this.agronomistService = agronomistService;
        this.loginService = loginService;
    }

    /**
     * Refreshes the tokens (access and refresh) when they are expired.
     * @param request
     * @param response
     * @throws IOException
     */
    @PostMapping("/token/refresh")
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException { loginService.refreshToken(request, response);}




}

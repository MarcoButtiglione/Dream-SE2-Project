package com.se2project.dream.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se2project.dream.entity.Agronomist;
import com.se2project.dream.entity.Farmer;
import com.se2project.dream.repository.AgronomistRepository;
import com.se2project.dream.repository.FarmRepository;
import com.se2project.dream.repository.FarmerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@Transactional
@Slf4j

/**
 * Service for Login.
 */
public class LoginService implements UserDetailsService {


        private final FarmerRepository farmerRepository;
        private final FarmRepository farmRepository;
        private final AgronomistRepository agronomistRepository;
        private final PasswordEncoder passwordEncoder;
        private final FarmerService farmerService;
        private final AgronomistService agronomistService;

        public LoginService(FarmerRepository farmerRepository, FarmRepository farmRepository, AgronomistRepository agronomistRepository, PasswordEncoder passwordEncoder, FarmerService farmerService, AgronomistService agronomistService) {
            this.farmerRepository = farmerRepository;
            this.farmRepository = farmRepository;
            this.agronomistRepository = agronomistRepository;
            this.passwordEncoder = passwordEncoder;
            this.farmerService = farmerService;
            this.agronomistService = agronomistService;
        }

    /**
     * Given the token, it is decoded and the id of the Farmer, owner of the
     * token, is returned.
      * @param token
     * @return id of the Farmer
     */
    public Long getIdFromTokenF(String token) {
            String tokenTmp = token.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(tokenTmp);
            String username = decodedJWT.getSubject();
            Farmer farmer = farmerRepository.findByEmail(username);
            Long id = farmer.getFarmerId();
            return id;
        }

    /**
     * Given the token, it is decoded and the id of the Agronomist, owner of the
     * token, is returned.
     * @param token
     * @return id of the Agronomist
     */
    public Long getIdFromTokenA(String token) {
            String tokenTmp = token.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(tokenTmp);
            String username = decodedJWT.getSubject();
            Agronomist agronomist = agronomistRepository.findByEmail(username);
            Long id = agronomist.getAgronomistId();
            return id;
        }


    /**
     * This method is used during the authentication process to check whether a user who is
     * trying to log in can do it because was previously registered and the credentials are correct
     * or if the user cannot log in because something went wrong.
      * @param email
     * @return the correspondent UserDetails Object
     * @throws UsernameNotFoundException
     */
    @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            Farmer farmer = farmerRepository.findByEmail(email);
            Agronomist agronomist = agronomistRepository.findByEmail(email);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

            if (farmer == null && agronomist == null){

                log.error("Farmer and Agronomist not found");
                throw new UsernameNotFoundException("Farmer and Agronomist not found");

            } else if (farmer != null && agronomist == null){

                authorities.add(new SimpleGrantedAuthority("Farmer"));
                User user = new User(farmer.getEmail(), farmer.getPassword(), authorities);
                log.info("Farmer found");
                return new User(farmer.getEmail(), farmer.getPassword(), authorities);

            }  else if (farmer == null && agronomist != null) {

                authorities.add(new SimpleGrantedAuthority("Agronomist"));
                User user = new User(agronomist.getEmail(), agronomist.getPassword(), authorities);
                log.info("Agronomist found");

                return new User(agronomist.getEmail(), agronomist.getPassword(), authorities);
            }

            return new User(farmer.getEmail(), farmer.getPassword(), authorities);

        }

    /**
     * This function is used to perform the process of refresh token. It is used when the
     * access token of a user is outdated and it needs to be refreshed. It used the refresh
     * token to generate two new tokens that are then passed onto the WebApp.
      * @param request
     * @param response
     * @throws IOException
     */
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Farmer farmer = farmerService.getFarmerByEmail(username);
                Agronomist agronomist = agronomistService.getAgronomistByEmail(username);

                if(farmer != null && agronomist == null) {
                    String access_token = JWT.create()
                            .withSubject(farmer.getEmail())
                            .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 24 * 60 * 1000))
                            .withIssuer(request.getRequestURL().toString())
                            .withClaim("roles", farmer.getRole().stream().collect(Collectors.toList()))
                            .sign(algorithm);


                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("access_token", access_token);
                    tokens.put("refresh_token", refresh_token);
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                } else if (farmer == null && agronomist != null) {
                    String access_token = JWT.create()
                            .withSubject(agronomist.getEmail())
                            .withExpiresAt(new Date(System.currentTimeMillis() + 90 * 24 * 60 * 1000))
                            .withIssuer(request.getRequestURL().toString())
                            .withClaim("roles", agronomist.getRole().stream().collect(Collectors.toList()))
                            .sign(algorithm);


                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("access_token", access_token);
                    tokens.put("refresh_token", refresh_token);
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                }
            } catch (Exception exception) {
                log.error("Error logging in: {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);

            }
        } else {
            throw new RuntimeException("Refresh Token is missing");
        }
    }

    }



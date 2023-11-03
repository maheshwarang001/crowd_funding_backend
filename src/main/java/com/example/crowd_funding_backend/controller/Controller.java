package com.example.crowd_funding_backend.controller;


import com.example.crowd_funding_backend.model.login.Login;
import com.example.crowd_funding_backend.model.user.SetPwd;
import com.example.crowd_funding_backend.model.user.User;
import com.example.crowd_funding_backend.repository.UserDB;
import com.example.crowd_funding_backend.security.UserInfoService;
import com.example.crowd_funding_backend.security.jwt.JwtService;
import com.example.crowd_funding_backend.service.email.EmailService;
import com.example.crowd_funding_backend.service.register.RegisterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequestMapping("/v1")
@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "http://localhost:3000")
public class Controller {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Autowired
    RegisterService registerService;

    @Autowired
    EmailService emailService;

    @Autowired
    UserDB userRepo;


    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/home")
    public String homepage() {
        return "home";
    }


    @PostMapping("/register-user/init")
    public ResponseEntity<Boolean> register(@RequestBody Map<String, String> requestMap) {

        String fname = requestMap.get("fname");
        String lname = requestMap.get("lname");
        String email = requestMap.get("email");
        String street = requestMap.get("street");
        String city = requestMap.get("city");
        String country = requestMap.get("country");
        String DOB = requestMap.get("DOB");

        if(fname.isEmpty() || lname.isEmpty() || email.isEmpty() || country.isEmpty() || DOB.isEmpty()) return ResponseEntity.badRequest().body(false);

        ResponseEntity<Boolean> response = registerService.registerUser(fname, lname, email, street, city, country, DOB);

        if (response.getStatusCode() == HttpStatus.OK) {

            log.info("Execute start");


            String base = "http://localhost:3000";
            StringBuilder strBuilder = new StringBuilder(base);
            strBuilder.append("/v1/verify/set?UUID=");

            // Handle success
            Runnable emailTask = () -> {

                try {
                    User user = userRepo.findByEmail(email);

                    log.info(user.getUserID().toString());
                    //link
                    strBuilder.append(user.getUserID());
                    log.info(strBuilder.toString());


                    emailService.sendVerificationEmail(email, strBuilder.toString());
                } catch (Exception e) {
                    throw e;
                }
            };
            executorService.submit(emailTask);
        }
        return response;

    }

    @PostMapping("/set-pwd/final")
    public ResponseEntity<HttpStatus> setPwd(@RequestBody SetPwd pwd) {

        if (pwd.getPwd().isEmpty()) {
            log.info("empty");
            return ResponseEntity.badRequest().build();
        }

        return registerService.setPwdService(pwd);
    }


    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody Login authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

}

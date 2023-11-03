package com.example.crowd_funding_backend.service.register;

import com.example.crowd_funding_backend.model.user.Address;
import com.example.crowd_funding_backend.model.user.SetPwd;
import com.example.crowd_funding_backend.model.user.User;
import com.example.crowd_funding_backend.repository.UserDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


import java.util.Base64;

@Service
@Slf4j
public class RegisterService {
    @Autowired
    UserDB userRepo;


    public ResponseEntity<Boolean> registerUser(String fname,
                                                   String lname,
                                                   String email,
                                                   String street,
                                                   String city,
                                                   String country,
                                                   String DOB
    ) {
        try {

            User userCheck = userRepo.findByEmail(email);

            if (userCheck == null) {
                Address address = new Address(street, city, country);
                User user = new User(fname, lname, email, address, DOB, false);
                userRepo.save(user);
                return ResponseEntity.ok(true);
            }
            else if (!userCheck.isCRegister()){
                return ResponseEntity.status(240).body(false);
            }
            else {
                return ResponseEntity.status(250).body(false);
            }


        } catch (Exception e) {
            log.error("Registration failed", e);
            return ResponseEntity.badRequest().body(false);
        }
    }

    public ResponseEntity<HttpStatus> setPwdService(SetPwd pwd) {
        if (pwd.getPwd().isEmpty()) {
            log.info("empty");
            return ResponseEntity.badRequest().build();
        }


        String hashedPassword = BCrypt.hashpw(pwd.getPwd(), BCrypt.gensalt());
        User user = userRepo.findByUserID(pwd.getId());

        if (user != null && !user.isCRegister()) {
            user.setPassword(hashedPassword);
            user.setCRegister(true);
            try {
                userRepo.save(user);
                return ResponseEntity.ok(HttpStatus.OK);
            } catch (Exception e) {
                log.info("not saved");
                return ResponseEntity.badRequest().build();
            }
        }

        log.info("something is wrong");
        return ResponseEntity.badRequest().build();
    }
}

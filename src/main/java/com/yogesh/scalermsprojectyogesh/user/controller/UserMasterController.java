package com.yogesh.scalermsprojectyogesh.user.controller;

import com.yogesh.scalermsprojectyogesh.user.model.AuthRequest;
import com.yogesh.scalermsprojectyogesh.user.model.UserMaster;
import com.yogesh.scalermsprojectyogesh.user.service.UserMasterService;
import com.yogesh.scalermsprojectyogesh.utility.AppConstant;
import com.yogesh.scalermsprojectyogesh.utility.JWTTokenUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserMasterController {

    private UserMasterService userMasterService;
    private JWTTokenUtility jwtTokenUtility;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserMasterController(UserMasterService userMasterService, JWTTokenUtility jwtTokenUtility, AuthenticationManager authenticationManager) {
        this.userMasterService = userMasterService;
        this.jwtTokenUtility = jwtTokenUtility;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registerUser")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserMaster userMaster) {
        return new ResponseEntity<>(userMasterService.create(userMaster), HttpStatus.CREATED);
    }

    @PostMapping("/generateToken")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> generateToken(@RequestBody @Valid AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUserName(), authRequest.getPassword()
        ));
        if(authentication.isAuthenticated()) {
            return ResponseEntity.ok(jwtTokenUtility.generateToken(authRequest.getUserName()));
        }else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PutMapping("/updateUser")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserMaster userMaster) {
        return new ResponseEntity<>(userMasterService.update(userMaster), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserMaster> getUser(@PathVariable String username) {
        return ResponseEntity.ok(userMasterService.readByUsername(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userMasterService.deleteById(id);
        return ResponseEntity.ok(AppConstant.DELETE_USER);
    }
}

package com.yogesh.scalermsprojectyogesh.user.controller;

import com.yogesh.scalermsprojectyogesh.exception.CustomUsernameNotFoundException;
import com.yogesh.scalermsprojectyogesh.user.model.AuthRequest;
import com.yogesh.scalermsprojectyogesh.user.model.UserMasterBean;
import com.yogesh.scalermsprojectyogesh.user.model.entity.UserMaster;
import com.yogesh.scalermsprojectyogesh.user.service.UserMasterService;
import com.yogesh.scalermsprojectyogesh.utility.AppConstant;
import com.yogesh.scalermsprojectyogesh.utility.JWTTokenUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Autowired
    private JWTTokenUtility jwtTokenUtility;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserMasterController(UserMasterService userMasterService) {
        this.userMasterService = userMasterService;
    }

    @PostMapping("/registerUser")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserMasterBean> registerUser(@Valid @RequestBody UserMasterBean userMasterBean) throws CustomUsernameNotFoundException {
        return new ResponseEntity<>(userMasterService.create(userMasterBean), HttpStatus.CREATED);
    }

    @PostMapping("/generateToken")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> generateToken(@RequestBody @Valid AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        if(authentication.isAuthenticated()) {
            return ResponseEntity.ok(jwtTokenUtility.generateToken(authRequest.getUserName()));
        }else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PutMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserMasterBean userMasterBean) {
        return new ResponseEntity<>(userMasterService.update(userMasterBean), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserMasterBean> getUser(@PathVariable String username) throws CustomUsernameNotFoundException {
        return ResponseEntity.ok(userMasterService.readByUsername(username));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok(userMasterService.deleteById(id));
    }

    //TODO: User add into family member API

}

package com.yogesh.scalermsprojectyogesh.user.controller;

import com.yogesh.scalermsprojectyogesh.exception.CustomUsernameNotFoundException;
import com.yogesh.scalermsprojectyogesh.user.model.UserMasterBean;
import com.yogesh.scalermsprojectyogesh.user.model.entity.UserMaster;
import com.yogesh.scalermsprojectyogesh.user.service.UserMasterService;
import com.yogesh.scalermsprojectyogesh.utility.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserMasterController {

    private UserMasterService userMasterService;
//    private JWTTokenUtility jwtTokenUtility;
//    private AuthenticationManager authenticationManager;

    @Autowired
    public UserMasterController(UserMasterService userMasterService) {
        this.userMasterService = userMasterService;
//        this.jwtTokenUtility = jwtTokenUtility;
//        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registerUser")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserMasterBean> registerUser(@Valid @RequestBody UserMasterBean userMasterBean) throws CustomUsernameNotFoundException {
        return new ResponseEntity<>(userMasterService.create(userMasterBean), HttpStatus.CREATED);
    }

    /*@PostMapping("/generateToken")
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
    }*/

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
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userMasterService.deleteById(id);
        return ResponseEntity.ok(AppConstant.DELETE_USER);
    }

    //TODO: exception handling only messeage has to come.
    //TODO: Role handling
    //TODO: userMaster improve with family member associate.
}

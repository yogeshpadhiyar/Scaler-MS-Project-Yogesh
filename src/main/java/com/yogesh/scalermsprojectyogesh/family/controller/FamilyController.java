package com.yogesh.scalermsprojectyogesh.family.controller;

import com.yogesh.scalermsprojectyogesh.family.model.FamilyMasterBean;
import com.yogesh.scalermsprojectyogesh.family.model.UserFundBean;
import com.yogesh.scalermsprojectyogesh.family.service.FamilyService;
import com.yogesh.scalermsprojectyogesh.family.service.UserFundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/family")
public class FamilyController {

    @Autowired
    private FamilyService familyService;
    @Autowired
    private UserFundService userFundService;

    @GetMapping("/{id}")
    public ResponseEntity<FamilyMasterBean> getFamily(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(familyService.readById(id));
    }

    @PutMapping("/updateFamilyFund")
    public ResponseEntity<FamilyMasterBean> updateFamilyFund(@RequestBody FamilyMasterBean familyMasterBean) throws Exception {
        return new ResponseEntity<>(familyService.update(familyMasterBean), HttpStatus.OK);
    }

    @PostMapping("/addFamilyFund")
    public ResponseEntity<FamilyMasterBean> addFamilyFund(@RequestBody FamilyMasterBean familyMasterBean) throws Exception {
        return new ResponseEntity<>(familyService.addFamilyFund(familyMasterBean), HttpStatus.OK);
    }

    @PostMapping("/memberFundAllotment")
    public ResponseEntity<UserFundBean> memberFundAllotment(@RequestBody UserFundBean userFundBean) throws Exception {
        return ResponseEntity.ok(userFundService.create(userFundBean));
    }

    @GetMapping("/getFundByUserFundId/{id}")
    public ResponseEntity<UserFundBean> getFundByUserFundId(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(userFundService.readById(id), HttpStatus.OK);
    }

    @GetMapping("/getFundByUser/{id}")
    public ResponseEntity<UserFundBean> getFundByUserId(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(userFundService.readByUserId(id), HttpStatus.OK);
    }

    @PostMapping("/memberFundUpdate")
    public ResponseEntity<UserFundBean> memberFundUpdate(@RequestBody UserFundBean userFundBean) throws Exception {
        return ResponseEntity.ok(userFundService.updateAvailableAmountById(userFundBean));
    }
}

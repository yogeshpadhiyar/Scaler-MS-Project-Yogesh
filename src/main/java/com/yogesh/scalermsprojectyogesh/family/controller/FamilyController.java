package com.yogesh.scalermsprojectyogesh.family.controller;

import com.yogesh.scalermsprojectyogesh.family.model.FamilyMasterBean;
import com.yogesh.scalermsprojectyogesh.family.service.FamilyService;
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

    @GetMapping("{id}")
    public ResponseEntity<FamilyMasterBean> getFamily(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(familyService.readById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<FamilyMasterBean> update(@RequestBody FamilyMasterBean familyMasterBean) throws Exception {
        return new ResponseEntity<>(familyService.update(familyMasterBean), HttpStatus.OK);
    }
}

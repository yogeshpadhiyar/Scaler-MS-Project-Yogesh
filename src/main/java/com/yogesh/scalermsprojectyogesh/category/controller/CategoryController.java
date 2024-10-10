package com.yogesh.scalermsprojectyogesh.category.controller;

import com.yogesh.scalermsprojectyogesh.category.model.CategoryBean;
import com.yogesh.scalermsprojectyogesh.category.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //TODO: add category
    @PostMapping("/addCategory")
    public ResponseEntity<CategoryBean> addCategory(@RequestBody CategoryBean categoryBean) throws Exception {
        return ResponseEntity.ok(categoryService.create(categoryBean));
    }


    //TODO: assign fund to category by categoryId
    @PutMapping("/updateCategoryFund")
    public ResponseEntity<CategoryBean> updateCategoryFundById(@RequestBody CategoryBean categoryBean) throws Exception {
        return new ResponseEntity<>(categoryService.update(categoryBean), HttpStatus.ACCEPTED);
    }

    //read category by id
    //read all categories by User id
    //read all categories by family id
    //soft delete
    //add back deleted category
}

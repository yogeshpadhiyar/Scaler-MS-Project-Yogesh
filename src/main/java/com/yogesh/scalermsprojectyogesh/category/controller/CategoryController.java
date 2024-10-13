package com.yogesh.scalermsprojectyogesh.category.controller;

import com.yogesh.scalermsprojectyogesh.category.model.CategoryBean;
import com.yogesh.scalermsprojectyogesh.category.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @PutMapping("/assignCategoryFund")
    public ResponseEntity<CategoryBean> updateCategoryFundById(@RequestBody CategoryBean categoryBean) throws Exception {
        return new ResponseEntity<>(categoryService.update(categoryBean), HttpStatus.ACCEPTED);
    }

    //read category by id
    @GetMapping("/getCategoryById/{id}")
    public ResponseEntity<CategoryBean> getCategoryById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(categoryService.readById(id));
    }

    //read all categories by User id
    @GetMapping("/getCategoryByUserId/{userId}")
    public ResponseEntity<List<CategoryBean>> getCategoryByUserId(@PathVariable Long userId) throws Exception {
        return ResponseEntity.ok(categoryService.readByUserId(userId));
    }

    //read all categories by family id
    @GetMapping("/getCategoryByFamilyId/{familyId}")
    public ResponseEntity<Map<Long,List<CategoryBean>>> getCategoryByFamilyId(@PathVariable Long familyId) throws Exception {
        return ResponseEntity.ok(categoryService.readByFamilyId(familyId));
    }

    //soft delete
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(categoryService.deleteById(id));
    }

    //add back deleted category
    @PutMapping("/addBackCategory/{id}")
    public ResponseEntity<CategoryBean> addBackCategory(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(categoryService.addBackCategory(id));
    }
}

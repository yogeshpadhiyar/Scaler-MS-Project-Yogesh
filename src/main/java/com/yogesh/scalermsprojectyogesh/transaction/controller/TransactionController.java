package com.yogesh.scalermsprojectyogesh.transaction.controller;

import com.yogesh.scalermsprojectyogesh.transaction.model.TransactionBean;
import com.yogesh.scalermsprojectyogesh.transaction.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@Slf4j
public class TransactionController {

    //TODO: Introduce trance-id for each request

    @Autowired
    private TransactionService transactionService;

    //create transaction
    @PostMapping("/create")
    public ResponseEntity<TransactionBean> createTransaction(@RequestBody TransactionBean transactionBean) throws Exception {
        log.info("Creating transaction");
        return ResponseEntity.ok(transactionService.create(transactionBean));
    }

    //read transaction by id
    @GetMapping("/getTransactionById/{id}")
    public ResponseEntity<TransactionBean> getTransactionById(@PathVariable Long id) throws Exception {
        log.info("Reading transaction by id");
        return ResponseEntity.ok(transactionService.readById(id));
    }


    //read all transactions by user id
    @GetMapping("/getTransactionByUserId/{userId}")
    public ResponseEntity<List<TransactionBean>> getTransactionByUserId(@PathVariable Long userId) throws Exception {
        log.info("Reading transaction by user id");
        return ResponseEntity.ok(transactionService.readByUserId(userId));
    }

    //read all transactions by category id for user
    @GetMapping("/getTransactionByCategoryId/{categoryId}")
    public ResponseEntity<List<TransactionBean>> getTransactionByCategoryId(@PathVariable Long categoryId) throws Exception {
        log.info("Reading transaction by category id");
        return ResponseEntity.ok(transactionService.readByCategoryId(categoryId));
    }

}

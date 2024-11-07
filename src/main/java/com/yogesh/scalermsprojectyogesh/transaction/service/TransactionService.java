package com.yogesh.scalermsprojectyogesh.transaction.service;

import com.yogesh.scalermsprojectyogesh.category.model.CategoryBean;
import com.yogesh.scalermsprojectyogesh.category.repository.CategoryRepository;
import com.yogesh.scalermsprojectyogesh.category.service.CategoryService;
import com.yogesh.scalermsprojectyogesh.exception.CategoryModuleException;
import com.yogesh.scalermsprojectyogesh.exception.TransactionModuleException;
import com.yogesh.scalermsprojectyogesh.service.CrudService;
import com.yogesh.scalermsprojectyogesh.transaction.model.TransactionBean;
import com.yogesh.scalermsprojectyogesh.transaction.model.entity.Transaction;
import com.yogesh.scalermsprojectyogesh.transaction.model.entity.TransactionType;
import com.yogesh.scalermsprojectyogesh.transaction.repository.TransactionRepository;
import com.yogesh.scalermsprojectyogesh.utility.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TransactionService implements CrudService<TransactionBean> {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CategoryService categoryService;

    @Override
    public TransactionBean create(TransactionBean transactionBean) throws Exception {
        // Check if the category is active
        if (!categoryService.readById(transactionBean.getCategoryId()).isActive()) {
            throw new CategoryModuleException(AppConstant.CATEGORY_NOT_ACTIVE);
        }

        // Check if the user needs approval for the transaction from a different user
        if (transactionBean.getApprovedUserId() != null && transactionBean.getApprovedUserId().equals(transactionBean.getUserId())) {
            //check if transaction is of type credit then add into category fund
            //check if transaction is of type debit then subtract from category fund before check if user has enough available fund
            categoryService.update(createCategoryBean(transactionBean));
            return transactionRepository.save(transactionBean.createEntityBean()).createResponseBean();
        }else {
            //TODO: future scope ....approval session management
            throw new TransactionModuleException(AppConstant.USER_NEEDS_APPROVAL);
        }

    }

    public CategoryBean createCategoryBean( TransactionBean transactionBean) {
        return CategoryBean.builder()
                .categoryId(transactionBean.getCategoryId())
                .userId(transactionBean.getUserId())
                .availableFund(transactionBean.getTransactionAmount())
                .transactionType(TransactionType.valueOf(transactionBean.getTransactionType()))
                .build();
    }

    @Override
    public TransactionBean readById(Long id) throws Exception {
        //read transaction by id
        return transactionRepository.findById(id).orElseThrow(() -> new TransactionModuleException(AppConstant.TRANSACTION_NOT_FOUND)).createResponseBean();
    }

    @Override
    public TransactionBean update(TransactionBean transactionBean) throws Exception {
        throw new TransactionModuleException(AppConstant.TRANSACTION_UPDATE_NOT_ALLOWED);
    }

    @Override
    public String deleteById(Long id) throws Exception {
        throw new TransactionModuleException(AppConstant.TRANSACTION_DELETE_NOT_ALLOWED);
    }

    public List<TransactionBean> readByUserId(Long userId) throws Exception {
        //read all transactions by user id
        return transactionRepository.findByUserId(userId).orElseThrow(() -> new TransactionModuleException(AppConstant.TRANSACTION_NOT_FOUND)).stream().map(Transaction::createResponseBean).toList();
    }

    public List<TransactionBean> readByCategoryId(Long categoryId) throws Exception{
        return transactionRepository.findByCategoryId(categoryId).orElseThrow(() -> new TransactionModuleException(AppConstant.TRANSACTION_NOT_FOUND)).stream().map(Transaction::createResponseBean).toList();
    }
}

package com.yogesh.scalermsprojectyogesh.category.service;

import com.yogesh.scalermsprojectyogesh.category.model.CategoryBean;
import com.yogesh.scalermsprojectyogesh.category.model.entity.Category;
import com.yogesh.scalermsprojectyogesh.category.repository.CategoryRepository;
import com.yogesh.scalermsprojectyogesh.exception.CategoryModuleException;
import com.yogesh.scalermsprojectyogesh.exception.CustomUsernameNotFoundException;
import com.yogesh.scalermsprojectyogesh.exception.UserFundModuleException;
import com.yogesh.scalermsprojectyogesh.family.model.UserFundBean;
import com.yogesh.scalermsprojectyogesh.family.repository.UserFundRepository;
import com.yogesh.scalermsprojectyogesh.service.CrudService;
import com.yogesh.scalermsprojectyogesh.user.model.entity.UserMaster;
import com.yogesh.scalermsprojectyogesh.user.repository.UserMasterRepository;
import com.yogesh.scalermsprojectyogesh.utility.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CategoryService implements CrudService<CategoryBean> {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserMasterRepository userMasterRepository;
    @Autowired
    private UserFundRepository userFundRepository;

    @Override
    public CategoryBean create(CategoryBean categoryBean) throws Exception {
        //TODO: if family id null then need to fetch from UserID
        if(categoryBean.getFamilyId()==null){
            UserMaster userMaster = userMasterRepository.findById(categoryBean.getUserId()).orElseThrow(()-> new CustomUsernameNotFoundException(AppConstant.USERID_NOT_FOUNT+categoryBean.getUserId()));
            categoryBean.setFamilyId(userMaster.getFamily().getId());
        }
        if(categoryBean.getTotalFund()!=null){
            categoryBean.setAvailableFund(categoryBean.getTotalFund());
        }

        //update user available fund
        UserFundBean userFundBean = userFundRepository.findByUserId(categoryBean.getUserId()).orElseThrow(()-> new UserFundModuleException(AppConstant.USER_FUND_NOT_ALLOT+categoryBean.getUserId())).createResponseBean();
        if(userFundBean.getAvailableAmount().compareTo(categoryBean.getTotalFund())>0){
            userFundBean.setAvailableAmount(userFundBean.getAvailableAmount().subtract(categoryBean.getTotalFund()));
        }else{
            throw new CategoryModuleException(AppConstant.CATEGORY_TOTALFUND_GREATERTHAN_AVAILABLEFUND);
        }
        userFundRepository.updateAvailableAmountByUserId(categoryBean.getUserId(), userFundBean.getAvailableAmount());
        categoryBean.setActive(true);
        CategoryBean outputBean = categoryRepository.save(categoryBean.createEntityBean()).createResponseBean();
        return outputBean;
    }

    @Override
    public CategoryBean readById(Long id) throws Exception {
        return null;
    }

    @Override
    public CategoryBean update(CategoryBean categoryBean) throws Exception {
        Category category = categoryRepository.findById(categoryBean.getCategoryId())
        return null;
    }

    @Override
    public void deleteById(Long id) throws Exception {

    }
}

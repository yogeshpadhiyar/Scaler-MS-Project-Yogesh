package com.yogesh.scalermsprojectyogesh.category.service;

import com.yogesh.scalermsprojectyogesh.category.model.CategoryBean;
import com.yogesh.scalermsprojectyogesh.category.repository.CategoryRepository;
import com.yogesh.scalermsprojectyogesh.exception.CustomUsernameNotFoundException;
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

    @Override
    public CategoryBean create(CategoryBean categoryBean) throws Exception {
        //TODO: if family id null then need to fetch from UserID
        if(categoryBean.getFamilyId()==null){
            UserMaster userMaster = userMasterRepository.findById(categoryBean.getUserId()).orElseThrow(()-> new CustomUsernameNotFoundException(AppConstant.USERID_NOT_FOUNT+categoryBean.getUserId()));
            categoryBean.setFamilyId(userMaster.getFamily().getId());
        }
        return categoryRepository.save(categoryBean.createEntityBean()).createResponseBean();
    }

    @Override
    public CategoryBean readById(Long id) throws Exception {
        return null;
    }

    @Override
    public CategoryBean update(CategoryBean category) throws Exception {
        return null;
    }

    @Override
    public void deleteById(Long id) throws Exception {

    }
}

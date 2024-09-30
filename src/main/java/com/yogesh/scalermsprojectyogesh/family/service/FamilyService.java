package com.yogesh.scalermsprojectyogesh.family.service;

import com.yogesh.scalermsprojectyogesh.exception.FamilyModuleException;
import com.yogesh.scalermsprojectyogesh.family.model.FamilyMasterBean;
import com.yogesh.scalermsprojectyogesh.family.model.entity.FamilyMaster;
import com.yogesh.scalermsprojectyogesh.family.repository.FamilyMasterRepository;
import com.yogesh.scalermsprojectyogesh.service.CrudService;
import com.yogesh.scalermsprojectyogesh.user.model.entity.UserMaster;
import com.yogesh.scalermsprojectyogesh.utility.AppConstant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.Optional;

@Slf4j
@Service
public class FamilyService implements CrudService<FamilyMasterBean> {
    @Autowired
    private FamilyMasterRepository familyMasterRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public FamilyMasterBean create(FamilyMasterBean familyMasterBean) throws Exception {
        throw new HttpRequestMethodNotSupportedException(AppConstant.METHOD_NOT_SUPPORT_EXCEPTION);
    }

    @Override
    public FamilyMasterBean readById(Long id) throws Exception {
        return familyMasterRepository.findById(id).orElseThrow(()-> new FamilyModuleException(AppConstant.FAMILY_ID_NOT_FOUNT+id)).createResponseBean();
    }

    @Override
    public FamilyMasterBean update(FamilyMasterBean familyMasterBean) throws Exception {
        if(familyMasterBean.getFamilyFund()<0){
            throw new FamilyModuleException(AppConstant.FAMILY_FUND_SHOULD_NOT_NEGATIVE);
        }
        Optional<FamilyMaster> familyMasterOpt = familyMasterRepository.findById(familyMasterBean.getId());
        if(familyMasterOpt.isEmpty())   throw new FamilyModuleException(AppConstant.FAMILY_ID_NOT_FOUNT+familyMasterBean.getId());

        familyMasterRepository.updateById(familyMasterBean.getId(), familyMasterBean.getFamilyFund());
        entityManager.clear();
        return familyMasterRepository.findById(familyMasterBean.getId()).get().createResponseBean();
    }

    @Override
    public void deleteById(Long id) throws Exception {
        throw new HttpRequestMethodNotSupportedException(AppConstant.METHOD_NOT_SUPPORT_EXCEPTION);
    }
}

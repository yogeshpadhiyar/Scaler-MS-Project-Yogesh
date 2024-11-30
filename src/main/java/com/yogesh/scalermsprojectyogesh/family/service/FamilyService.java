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


    public FamilyMasterBean addFamilyFund(FamilyMasterBean familyMasterBean) throws Exception {
        if(familyMasterBean.getFamilyFund().doubleValue()<0){
            throw new FamilyModuleException(AppConstant.FAMILY_FUND_SHOULD_NOT_NEGATIVE);
        }
        Optional<FamilyMaster> familyMasterOpt = familyMasterRepository.findById(familyMasterBean.getId());
        if(familyMasterOpt.isEmpty())   throw new FamilyModuleException(AppConstant.FAMILY_ID_NOT_FOUNT+familyMasterBean.getId());

        familyMasterBean.setAvailableFamilyFund(familyMasterBean.getFamilyFund());
        familyMasterRepository.updateById(familyMasterBean.getId(), familyMasterBean.getFamilyFund(), familyMasterBean.getAvailableFamilyFund());
        entityManager.clear();
        return familyMasterRepository.findById(familyMasterBean.getId()).get().createResponseBean();
    }

    @Override
    public FamilyMasterBean readById(Long id) throws Exception {
        return familyMasterRepository.findById(id).orElseThrow(()-> new FamilyModuleException(AppConstant.FAMILY_ID_NOT_FOUNT+id)).createResponseBean();
    }

    @Override
    public FamilyMasterBean update(FamilyMasterBean familyMasterBean) throws Exception {
        Optional<FamilyMaster> familyMasterOpt = familyMasterRepository.findById(familyMasterBean.getId());
        if(familyMasterOpt.isEmpty())   throw new FamilyModuleException(AppConstant.FAMILY_ID_NOT_FOUNT+familyMasterBean.getId());
        FamilyMaster familyMaster = familyMasterOpt.get();
        if(familyMasterBean.getAddAdditionalFund()!=null) {
            familyMaster.setFamilyFund(familyMaster.getFamilyFund().add(familyMasterBean.getAddAdditionalFund()));
            familyMaster.setAvailableFamilyFund(familyMaster.getAvailableFamilyFund().add(familyMasterBean.getAddAdditionalFund()));
        }
        familyMasterRepository.updateById(familyMaster.getId(), familyMaster.getFamilyFund(), familyMaster.getAvailableFamilyFund());
        entityManager.clear();
        return familyMasterRepository.findById(familyMaster.getId()).get().createResponseBean();
    }

    @Override
    public String deleteById(Long id) throws Exception {
        throw new HttpRequestMethodNotSupportedException(AppConstant.METHOD_NOT_SUPPORT_EXCEPTION);
    }

    public FamilyMasterBean readByName(String familyName) throws Exception{
        return familyMasterRepository.findByFamilyName(familyName).orElseThrow(()-> new FamilyModuleException(AppConstant.FAMILY_NAME_NOT_FOUNT+familyName)).createResponseBean();
    }
}

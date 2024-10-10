package com.yogesh.scalermsprojectyogesh.family.service;

import com.yogesh.scalermsprojectyogesh.exception.FamilyModuleException;
import com.yogesh.scalermsprojectyogesh.exception.UserFundModuleException;
import com.yogesh.scalermsprojectyogesh.family.model.FamilyMasterBean;
import com.yogesh.scalermsprojectyogesh.family.model.UserFundBean;
import com.yogesh.scalermsprojectyogesh.family.model.entity.FamilyMaster;
import com.yogesh.scalermsprojectyogesh.family.model.entity.UserFund;
import com.yogesh.scalermsprojectyogesh.family.repository.FamilyMasterRepository;
import com.yogesh.scalermsprojectyogesh.family.repository.UserFundRepository;
import com.yogesh.scalermsprojectyogesh.service.CrudService;
import com.yogesh.scalermsprojectyogesh.user.model.UserMasterBean;
import com.yogesh.scalermsprojectyogesh.user.model.entity.UserMaster;
import com.yogesh.scalermsprojectyogesh.user.repository.UserMasterRepository;
import com.yogesh.scalermsprojectyogesh.user.service.UserMasterService;
import com.yogesh.scalermsprojectyogesh.utility.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@Service
public class UserFundService implements CrudService<UserFundBean> {

    @Autowired
    private UserFundRepository userFundRepository;
    @Autowired
    private UserMasterService userMasterService;
    @Autowired
    private FamilyMasterRepository familyMasterRepository;

    @Override
    public UserFundBean create(UserFundBean userFundBean) throws Exception {
        FamilyMaster familyMaster = familyMasterRepository.findById(userFundBean.getFamilyId()).orElseThrow(()-> new FamilyModuleException(AppConstant.FAMILY_ID_NOT_FOUNT));
        UserFund userFund = userFundBean.createEntityBean();
        //check both user into same family or not.
        if(familyMaster.getUsers().stream().filter(um -> um.getId().equals(userFund.getUserId())).count()!=1){
            throw new UserFundModuleException(AppConstant.USER_NOT_FOUND_INTO_FAMILY);
        }
        if(familyMaster.getUsers().stream().filter(um -> um.getId().equals(userFund.getFundAssignerUserId())).count()!=1){
            throw new UserFundModuleException(AppConstant.USER_NOT_FOUND_INTO_FAMILY);
        }
        if(userFund.getTotalFundAmount()!=null)     userFund.setAvailableAmount(userFund.getTotalFundAmount());
        userFundBean = userFundRepository.saveAndFlush(userFund).createResponseBean();
        //Decrease family available fund
        familyMaster.setAvailableFamilyFund(familyMaster.getAvailableFamilyFund().subtract(userFundBean.getTotalFundAmount()));
        familyMasterRepository.updateById(familyMaster.getId(),familyMaster.getFamilyFund(), familyMaster.getAvailableFamilyFund());
        return userFundBean;
    }

    @Override
    public UserFundBean readById(Long id) throws Exception {
        return userFundRepository.findById(id).orElseThrow(()-> new UserFundModuleException(AppConstant.USER_FUND_ID_NOT_FOUNT+id)).createResponseBean();
    }

    @Override
    public UserFundBean update(UserFundBean userFundBean) throws Exception {
        return null;
    }

    @Override
    public void deleteById(Long id) throws Exception {
        throw new HttpRequestMethodNotSupportedException(AppConstant.METHOD_NOT_SUPPORT_EXCEPTION);
    }

    public UserFundBean readByUserId(Long userId) throws Exception {
        return userFundRepository.findByUserId(userId).orElseThrow(()-> new UserFundModuleException(AppConstant.USER_FUND_NOT_ALLOT+userId)).createResponseBean();
    }

    public String updateAvailableAmountById(UserFundBean userFundBean) throws Exception {
        Integer affectedUsers =  userFundRepository.updateAvailableAmountByUserId(userFundBean.getUserId(), userFundBean.getAvailableAmount());
        return AppConstant.FAMILY_AFFECTED_USERS+affectedUsers;
    }
}

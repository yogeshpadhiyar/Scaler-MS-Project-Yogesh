package com.yogesh.scalermsprojectyogesh.user.service;

import com.yogesh.scalermsprojectyogesh.exception.CustomUsernameNotFoundException;
import com.yogesh.scalermsprojectyogesh.service.CrudService;
import com.yogesh.scalermsprojectyogesh.user.model.UserMasterBean;
import com.yogesh.scalermsprojectyogesh.family.model.entity.FamilyMaster;
import com.yogesh.scalermsprojectyogesh.user.model.entity.UserMaster;
import com.yogesh.scalermsprojectyogesh.family.repository.FamilyMasterRepository;
import com.yogesh.scalermsprojectyogesh.user.repository.RoleRepository;
import com.yogesh.scalermsprojectyogesh.user.repository.UserMasterRepository;
import com.yogesh.scalermsprojectyogesh.utility.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserMasterService implements CrudService<UserMasterBean> {

    @Autowired
    private UserMasterRepository userMasterRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FamilyMasterRepository familyMasterRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMasterRepository.findByUsername(username).map(UserDetailsImpl::new).orElseThrow(()-> new UsernameNotFoundException("User not found with username :: "+username));

    }*/

    @Override
    public UserMasterBean create(UserMasterBean userMasterBean) throws CustomUsernameNotFoundException {
//        userMaster.setPassword(userMaster.getPassword());
        if(userMasterRepository.findByUsername(userMasterBean.getUsername()).isPresent()){
            throw new CustomUsernameNotFoundException(AppConstant.USERNAME_FOUNT+userMasterBean.getUsername());
        }
        if(userMasterRepository.findByEmailId(userMasterBean.getEmailId()).isPresent()){
            throw new CustomUsernameNotFoundException(AppConstant.EMAIL_ID_FOUNT_IN_USER +userMasterBean.getEmailId());
        }
        UserMaster userMaster = userMasterBean.createEntityBean();
        Optional<FamilyMaster> familyMasterOpt =  familyMasterRepository.findByFamilyName(userMasterBean.getFamilyName());
        FamilyMaster familyMaster;
        if(userMasterBean.getIsParentOfFamily() && familyMasterOpt.isPresent()){
            throw new CustomUsernameNotFoundException(AppConstant.FAMILY_NAME_FOUNT+userMasterBean.getFamilyName());
        }else if(userMasterBean.getIsParentOfFamily()){
            familyMaster = familyMasterRepository.save(FamilyMaster.builder().familyName(userMasterBean.getFamilyName()).build());
        }else if (familyMasterOpt.isEmpty()){
            throw new CustomUsernameNotFoundException(AppConstant.FAMILY_NAME_NOT_FOUNT+userMasterBean.getFamilyName());
        }else{
            familyMaster = familyMasterOpt.get();
            userMaster.setParentUser(familyMaster.getUsers().stream().filter(UserMaster::getIsParentOfFamily).findFirst().get());
        }

        userMaster.setRoles(roleRepository.findAllByRoleNameIn(userMasterBean.getRoles()));
        userMaster.setFamily(familyMaster);
        userMaster.setPassword(this.passwordEncoder.encode(userMaster.getPassword()));
        return userMasterRepository.save(userMaster).createResponseBean();
    }

    @Override
    public UserMasterBean readById(Long id) throws CustomUsernameNotFoundException{
        return userMasterRepository.findById(id).orElseThrow(()-> new CustomUsernameNotFoundException(AppConstant.USERID_NOT_FOUNT+id)).createResponseBean();
    }

    @Override
    public UserMasterBean update(UserMasterBean userMasterBean) {
//        userMaster.setPassword(userMaster.getPassword());
        UserMaster userMaster = userMasterBean.createEntityBean();
        userMaster.setRoles(roleRepository.findAllByRoleNameIn(userMasterBean.getRoles()));
        return userMasterRepository.saveAndFlush(userMaster).createResponseBean();
    }

    @Override
    public String deleteById(Long id) throws Exception{
        UserMaster userMaster = userMasterRepository.findById(id).orElseThrow(()-> new CustomUsernameNotFoundException(AppConstant.USERID_NOT_FOUNT+id));
        userMasterRepository.delete(userMaster);
        return AppConstant.DELETE_USER;
    }

    public UserMasterBean readByUsername(String username) throws CustomUsernameNotFoundException {
        return userMasterRepository.findByUsername(username).orElseThrow(()-> new CustomUsernameNotFoundException(AppConstant.USER_NOT_FOUNT +username)).createResponseBean();
    }
}

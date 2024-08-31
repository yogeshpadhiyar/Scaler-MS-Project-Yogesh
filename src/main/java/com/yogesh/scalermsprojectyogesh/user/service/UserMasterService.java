package com.yogesh.scalermsprojectyogesh.user.service;

import com.yogesh.scalermsprojectyogesh.service.CrudService;
import com.yogesh.scalermsprojectyogesh.user.model.UserDetailsImpl;
import com.yogesh.scalermsprojectyogesh.user.model.UserMaster;
import com.yogesh.scalermsprojectyogesh.user.repository.UserMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMasterService implements UserDetailsService , CrudService<UserMaster> {

    private UserMasterRepository userMasterRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserMasterService(UserMasterRepository userMasterRepository) {
        this.userMasterRepository = userMasterRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMasterRepository.findByUserName(username).map(UserDetailsImpl::new).orElseThrow(()-> new UsernameNotFoundException("User not found with username :: "+username));

    }

    @Override
    public UserMaster create(UserMaster userMaster) {
        userMaster.setPassword(passwordEncoder.encode(userMaster.getPassword()));
        return userMasterRepository.save(userMaster);
    }

    @Override
    public UserMaster readById(Long id) {
        return userMasterRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found with id :: "+id));
    }

    @Override
    public UserMaster update(UserMaster userMaster) {
        userMaster.setPassword(passwordEncoder.encode(userMaster.getPassword()));
        return userMasterRepository.saveAndFlush(userMaster);
    }

    @Override
    public void deleteById(Long id) {
        userMasterRepository.deleteById(id);
    }
}

package com.yogesh.scalermsprojectyogesh.user.service;

import com.yogesh.scalermsprojectyogesh.user.model.entity.Role;
import com.yogesh.scalermsprojectyogesh.user.model.entity.UserDetailsImpl;
import com.yogesh.scalermsprojectyogesh.user.model.entity.UserMaster;
import com.yogesh.scalermsprojectyogesh.user.repository.RoleRepository;
import com.yogesh.scalermsprojectyogesh.user.repository.UserMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserMasterRepository userMasterRepository;
//
//	@Autowired
//	private RoleRepository roleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//		Optional<UserMaster> user = this.userMasterRepository.findByUsername(username);
//		if(user.isPresent()) {
//		List<Role> roles =	this.roleRepository.findAllByUserId(user.get().getId());
//		}
		return userMasterRepository.findByUsername(username).map(UserDetailsImpl::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username :: " + username));


	}

}

package com.patrick.restapi.security;

import com.patrick.restapi.entity.MstRoleEntity;
import com.patrick.restapi.entity.MstUserEntity;
import com.patrick.restapi.repository.MstUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private  final MstUserRepository userMstRepository;

    public CustomerDetailsService(MstUserRepository userMstRepository) {

        this.userMstRepository = userMstRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MstUserEntity user = userMstRepository.findByUsername(username).orElseThrow(() ->new UsernameNotFoundException("USERNAME NOT FOUND"));
        return new User(user.getUsername(),user.getUsername(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<MstRoleEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleAccess()))
                .collect(Collectors.toList());
    }

}

package com.java.component;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //将权限和角色全部添加到authorities中，角色必须以'ROLE_'开头,否则会报错
        return User.withUsername(username).password("$2a$10$S42uBlg3EwW/bg2WZw3H7ehWLxPnXm67Tq2bz3WgSNl3Ac6t6XkPK").authorities("admin:*","ROLE_admin").build();
    }

}

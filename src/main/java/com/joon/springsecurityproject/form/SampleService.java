package com.joon.springsecurityproject.form;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SampleService {

    @Secured("ROLE_USER") //=@RolesAllowed("ROLE_USER"),  @PreAuthorize("hasRole('USER'") 메소드 호울 이전에 검사
   //RoleHierarchyImpl를 사용하지 않을 경우    @Secured({"ROLE_USER", "ROLE_ADMIN"}) 로 권한을 줄 수 있음

    public void dashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();//권한 정보
        Object credentials = authentication.getCredentials();
        boolean authenticated = authentication.isAuthenticated();//인증된 사용자인지 확인
    }
}

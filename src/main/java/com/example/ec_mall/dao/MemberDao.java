package com.example.ec_mall.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;


public class MemberDao {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoleDao {
        private Long accountId;
        private Long authorityId;
        private String roles;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDao implements UserDetails {
        private Long accountId;
        private String email;
        private String nickName;
        private String password;
        private String createdBy;
        private LocalDateTime createdDate;
        private String updatedBy;
        private LocalDateTime updatedDate;
        private RoleDao roles;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities () {
            ArrayList<GrantedAuthority> roleList = new ArrayList<>();
            roleList.add(new SimpleGrantedAuthority(roles.getRoles()));
            return roleList;
        }

        @Override
        public String getUsername () {
            return email;
        }
        @Override
        public boolean isAccountNonExpired () {
            return false;
        }

        @Override
        public boolean isAccountNonLocked () {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired () {
            return false;
        }

        @Override
        public boolean isEnabled () {
            return false;
        }
    }
}

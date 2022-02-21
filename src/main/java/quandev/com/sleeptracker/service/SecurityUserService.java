package quandev.com.sleeptracker.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import quandev.com.sleeptracker.entity.UserEntity;
import quandev.com.sleeptracker.repo.UserRepo;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepo.findByUsername(username);
        if (!userEntity.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(userEntity.get());

    }

    @Data
    @AllArgsConstructor
    public class CustomUserDetails implements UserDetails {

        UserEntity userEntity;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole()));
        }

        @Override
        public String getPassword() {
            return userEntity.getPassword();
        }

        @Override
        public String getUsername() {
            return userEntity.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}

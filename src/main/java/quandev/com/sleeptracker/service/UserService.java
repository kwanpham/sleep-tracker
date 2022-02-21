package quandev.com.sleeptracker.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import quandev.com.sleeptracker.dto.request.LoginRequest;
import quandev.com.sleeptracker.dto.request.SignupRequest;
import quandev.com.sleeptracker.dto.request.UserEditRequest;
import quandev.com.sleeptracker.dto.response.LoginResponse;
import quandev.com.sleeptracker.entity.UserEntity;
import quandev.com.sleeptracker.repo.UserRepo;
import quandev.com.sleeptracker.security.JwtTokenProvider;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    public List<UserEntity> listAllUser() {
        return userRepo.findAll();
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        SecurityUserService.CustomUserDetails customUserDetails = (SecurityUserService.CustomUserDetails) authentication.getPrincipal();
        String jwtToken = jwtTokenProvider.generateToken(customUserDetails);
        loginResponse.setJwtToken(jwtToken);

        log.info("account : {} - authentication success ", loginRequest.getUsername());


        return loginResponse;
    }

    public void signup(SignupRequest signupRequest) {
        UserEntity userEntity = new UserEntity()
                .setUsername(signupRequest.getUsername())
                .setPassword(passwordEncoder.encode(signupRequest.getPassword()))
                .setEmail(signupRequest.getEmail())
                .setFullname(signupRequest.getFullname())
                .setRole("user")
                .setCreatedTime(LocalDateTime.now());
        userRepo.save(userEntity);

    }

    public void editUser(UserEditRequest userEditRequest) {
        UserEntity userEntity = userRepo.findById(userEditRequest.getId()).get();


        userEntity.setFullname(userEditRequest.getFullname());
        userEntity.setPassword(passwordEncoder.encode(userEditRequest.getPassword()));
        userEntity.setEmail(userEditRequest.getEmail());

        userRepo.save(userEntity);

    }


}

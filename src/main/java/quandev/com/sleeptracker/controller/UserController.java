package quandev.com.sleeptracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import quandev.com.sleeptracker.dto.request.LoginRequest;
import quandev.com.sleeptracker.dto.request.SignupRequest;
import quandev.com.sleeptracker.dto.request.UserEditRequest;
import quandev.com.sleeptracker.dto.response.LoginResponse;
import quandev.com.sleeptracker.entity.SleepEntryEntity;
import quandev.com.sleeptracker.entity.UserEntity;
import quandev.com.sleeptracker.service.SecurityUserService;
import quandev.com.sleeptracker.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<UserEntity> listAllST() {
        return userService.listAllUser();
    }


    @PostMapping("/login")
    public LoginResponse loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }


    @PostMapping("/signup")
    public HttpStatus signup(@Valid @RequestBody SignupRequest signupRequest) {
        userService.signup(signupRequest);
        return HttpStatus.CREATED;
    }

    @PutMapping("/edit")
    public HttpStatus edit(@Valid @RequestBody UserEditRequest userEditRequest) {
        userService.editUser(userEditRequest);
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return HttpStatus.OK;
    }

}

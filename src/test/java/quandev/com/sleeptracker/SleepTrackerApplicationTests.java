package quandev.com.sleeptracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import quandev.com.sleeptracker.entity.UserEntity;
import quandev.com.sleeptracker.repo.UserRepo;

import java.time.LocalDateTime;

@SpringBootTest
class SleepTrackerApplicationTests {


    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        UserEntity userEntity = new UserEntity()
                .setRole("admin")
                .setUsername("admin")
                .setFullname("Admin Le")
                .setPassword(passwordEncoder.encode("12345"))
                .setEmail("admin@gmail.com")
                .setCreatedTime(LocalDateTime.now());

        userRepo.save(userEntity);


    }

}

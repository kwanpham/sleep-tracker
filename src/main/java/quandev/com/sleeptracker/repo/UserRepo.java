package quandev.com.sleeptracker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import quandev.com.sleeptracker.entity.UserEntity;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity , Long> {

    Optional<UserEntity> findByUsername(String username);

}

package quandev.com.sleeptracker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import quandev.com.sleeptracker.entity.SleepEntryEntity;
import quandev.com.sleeptracker.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SleepEntryRepo extends JpaRepository<SleepEntryEntity , Long> {

    List<SleepEntryEntity> findByUserEntity(UserEntity userEntity);

    Optional<SleepEntryEntity> findByUserEntityAndSleepDate(UserEntity userEntity , LocalDate localDate);

    void deleteByIdAndUserEntity(Long id , UserEntity userEntity);

}

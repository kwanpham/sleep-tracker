package quandev.com.sleeptracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quandev.com.sleeptracker.dto.request.SleepEntryCreateRequest;
import quandev.com.sleeptracker.dto.request.SleepEntryDeleteRequest;
import quandev.com.sleeptracker.dto.request.SleepEntryEditRequest;
import quandev.com.sleeptracker.entity.SleepEntryEntity;
import quandev.com.sleeptracker.entity.UserEntity;
import quandev.com.sleeptracker.repo.SleepEntryRepo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Transactional
@Service
public class SleepTrackerService {

    @Autowired
    SleepEntryRepo sleepEntryRepo;

    public List<SleepEntryEntity> listAllSleepEntryByUser(Long userId) {
        UserEntity userEntity = new UserEntity().setId(userId);

        return sleepEntryRepo.findByUserEntity(userEntity);
    }

    public void addnewSleepEntry(SleepEntryCreateRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateSleep = LocalDate.parse(request.getDate(), formatter);
        LocalTime localTimeSleep = LocalTime.parse(request.getSleepTime());
        LocalTime localTimeWakeup = LocalTime.parse(request.getWakeupTime());

        SleepEntryEntity sleepEntryEntity = new SleepEntryEntity()
                .setSleepDate(dateSleep)
                .setSleepTime(localTimeSleep)
                .setWakeupTime(localTimeWakeup)
                .setUserEntity(new UserEntity().setId(request.getUserId()))
                .setTotalSleepTime(ChronoUnit.MINUTES.between(localTimeSleep, localTimeWakeup))
                .setNote(request.getNote());

        sleepEntryRepo.save(sleepEntryEntity);

    }

    public void editSleepEntry(SleepEntryEditRequest request) {

        SleepEntryEntity sleepEntryEntity = sleepEntryRepo.findById(request.getId()).get();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateSleep = LocalDate.parse(request.getDate(), formatter);
        LocalTime localTimeSleep = LocalTime.parse(request.getSleepTime());
        LocalTime localTimeWakeup = LocalTime.parse(request.getWakeupTime());

        sleepEntryEntity
                .setSleepDate(dateSleep)
                .setSleepTime(localTimeSleep)
                .setWakeupTime(localTimeWakeup)
                .setNote(request.getNote());

        sleepEntryRepo.save(sleepEntryEntity);

    }

    public void deleteSleepEntry(SleepEntryDeleteRequest request) {
        sleepEntryRepo.deleteByIdAndUserEntity(request.getId() , new UserEntity().setId(request.getUserId()));
    }


    public String averageSleepDuration(int weekNumber , Long userId) {
        List<LocalDate> allDayInWeek = getAllDaysOfTheWeek(weekNumber);

        List<SleepEntryEntity> sleepEntryEntities = new ArrayList<>();

        allDayInWeek.forEach(temp -> {
            Optional<SleepEntryEntity> optional = sleepEntryRepo.findByUserEntityAndSleepDate(new UserEntity().setId(userId) , temp);
            optional.ifPresent(sleepEntryEntities::add);
        });

        double sumMinSleep = 0;
        for (SleepEntryEntity s : sleepEntryEntities) {
            sumMinSleep += s.getTotalSleepTime();
        }

        return String.format("%,.2f", (sumMinSleep/sleepEntryEntities.size())/60) ;

    }

    public String averageSleepTime(int weekNumber , Long userId) {
        List<LocalDate> allDayInWeek = getAllDaysOfTheWeek(weekNumber);

        List<SleepEntryEntity> sleepEntryEntities = new ArrayList<>();

        allDayInWeek.forEach(temp -> {
            Optional<SleepEntryEntity> optional = sleepEntryRepo.findByUserEntityAndSleepDate(new UserEntity().setId(userId) , temp);
            optional.ifPresent(sleepEntryEntities::add);
        });

        long nanosSum = 0;
        for (SleepEntryEntity s : sleepEntryEntities) {
            nanosSum += s.getSleepTime().toNanoOfDay();
        }

        return LocalTime.ofNanoOfDay(nanosSum / (sleepEntryEntities.size())).toString();

    }

    public String averageWakeUpTime(int weekNumber , Long userId) {
        List<LocalDate> allDayInWeek = getAllDaysOfTheWeek(weekNumber);

        List<SleepEntryEntity> sleepEntryEntities = new ArrayList<>();

        allDayInWeek.forEach(temp -> {
            Optional<SleepEntryEntity> optional = sleepEntryRepo.findByUserEntityAndSleepDate(new UserEntity().setId(userId) , temp);
            optional.ifPresent(sleepEntryEntities::add);
        });

        long nanosSum = 0;
        for (SleepEntryEntity s : sleepEntryEntities) {
            nanosSum += s.getWakeupTime().toNanoOfDay();
        }

        return LocalTime.ofNanoOfDay(nanosSum / (sleepEntryEntities.size())).toString();

    }

    private LocalDate getFirstDayOfWeek(int weekNumber) {
        return LocalDate
                .of(Year.now().getValue(), 2, 1)
                .with(WeekFields.of(Locale.ENGLISH).getFirstDayOfWeek())
                .with(WeekFields.of(Locale.ENGLISH).weekOfWeekBasedYear(), weekNumber);
    }

    private List<LocalDate> getAllDaysOfTheWeek(int weekNumber) {
        LocalDate firstDayOfWeek = getFirstDayOfWeek(weekNumber);
        return IntStream
                .rangeClosed(0, 6)
                .mapToObj(i -> firstDayOfWeek.plusDays(i))
                .collect(Collectors.toList());
    }


}

package quandev.com.sleeptracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import quandev.com.sleeptracker.dto.request.SleepEntryCreateRequest;
import quandev.com.sleeptracker.dto.request.SleepEntryDeleteRequest;
import quandev.com.sleeptracker.dto.request.SleepEntryEditRequest;
import quandev.com.sleeptracker.entity.SleepEntryEntity;
import quandev.com.sleeptracker.service.SleepTrackerService;
import java.util.List;

@RestController
@RequestMapping("/api/st")
public class SleepTrackController {

    @Autowired
    SleepTrackerService sleepTrackerService;

    @GetMapping("/list/{userId}")
    public List<SleepEntryEntity> listAllST(@PathVariable Long userId) {
       return sleepTrackerService.listAllSleepEntryByUser(userId);
    }


    @PostMapping("/add")
    public HttpStatus addnewSleepEntry(@RequestBody  SleepEntryCreateRequest request) {
        sleepTrackerService.addnewSleepEntry(request);
        return HttpStatus.CREATED;

    }

    @PutMapping("/edit")
    public HttpStatus editSleepEntry(@RequestBody SleepEntryEditRequest request) {
        sleepTrackerService.editSleepEntry(request);
        return HttpStatus.OK;

    }

    @DeleteMapping("/delete")
    public HttpStatus delete(@RequestBody SleepEntryDeleteRequest request) {
        sleepTrackerService.deleteSleepEntry(request);
        return HttpStatus.OK;
    }


    @GetMapping("/averageSleepDuration/")
    public String averageSleepDuration(@RequestParam(name = "userId") Long userId, @RequestParam(name = "weekNumber") Integer weekNumber) {
        return sleepTrackerService.averageSleepDuration(weekNumber , userId);
    }

    @GetMapping("/averageSleepTime/")
    public String averageSleepTime(@RequestParam(name = "userId") Long userId, @RequestParam(name = "weekNumber") Integer weekNumber) {
        return sleepTrackerService.averageSleepTime(weekNumber , userId);
    }


    @GetMapping("/averageWakeUpTime/")
    public String averageWakeUpTime(@RequestParam(name = "userId") Long userId, @RequestParam(name = "weekNumber") Integer weekNumber) {
        return sleepTrackerService.averageWakeUpTime(weekNumber , userId);
    }


}

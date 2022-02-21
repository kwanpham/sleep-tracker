package quandev.com.sleeptracker.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SleepEntryCreateRequest {

    @NotNull
    private Long userId;

    private String date;

    private String sleepTime;

    private String wakeupTime;

    private String note;



}

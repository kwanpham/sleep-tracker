package quandev.com.sleeptracker.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SleepEntryEditRequest {

    @NotNull
    private Long id;

    private String date;

    private String sleepTime;

    private String wakeupTime;

    private String note;



}

package quandev.com.sleeptracker.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SleepEntryDeleteRequest {

    @NotBlank
    private Long id;

    @NotBlank
    private Long userId;


}

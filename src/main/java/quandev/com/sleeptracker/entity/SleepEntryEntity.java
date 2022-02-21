package quandev.com.sleeptracker.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "st_sleep_entry")
public class SleepEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sleep_date")
    private LocalDate sleepDate;

    @Column(name = "sleep_time")
    private LocalTime sleepTime;

    @Column(name = "wakeup_time")
    private LocalTime wakeupTime;

    @Column(name = "total_sleep_time")
    private Long totalSleepTime;

    @Column(name = "note" , columnDefinition = "text")
    private String note;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;


}

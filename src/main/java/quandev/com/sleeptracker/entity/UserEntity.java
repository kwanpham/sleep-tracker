package quandev.com.sleeptracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "st_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username" , unique = true)
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "email" , unique = true)
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity")
    private List<SleepEntryEntity> sleepEntryList = new ArrayList<>();


}

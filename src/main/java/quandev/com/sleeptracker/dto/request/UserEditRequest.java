package quandev.com.sleeptracker.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserEditRequest {

    @NotNull
    private Long id;

    @Length(min = 3)
    private String username;

    @Length(min = 5)
    private String password;

    @NotBlank
    private String fullname;

    @Email
    private String email;


}

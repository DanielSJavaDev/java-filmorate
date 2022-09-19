package filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {

    int id = 1;

    @Email
    String email;

    @NotNull
    @NotBlank
    String login;

    String name = "";

    @Past
    LocalDate birthday;
}
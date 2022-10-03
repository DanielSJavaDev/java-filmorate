package filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    Set<Integer> friends = new HashSet<>();
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
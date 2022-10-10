package filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private final Set<Integer> friends = new HashSet<>();
    private int id = 0;

    @Email
    private String email;

    @NotNull
    @NotBlank
    private String login;

    private String name = "";

    @Past
    private LocalDate birthday;
}
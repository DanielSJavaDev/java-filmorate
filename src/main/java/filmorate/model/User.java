package filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class User {
    private Integer id = 0;
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String login;

    private String name = "";

    @Past
    private LocalDate birthday;

    //private final Set<Integer> friends = new HashSet<>();
}
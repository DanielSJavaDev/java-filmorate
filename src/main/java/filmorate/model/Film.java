package filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    Set<User> liked = new HashSet<>();
    int likes = 0;

    int id = 1;

    @NotNull
    @NotBlank
    String name;

    @Size(max = 200)
    String description;

    @Past
    LocalDate releaseDate;

    @Positive
    long duration;
}

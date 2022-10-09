package filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    Set<Integer> liked = new HashSet<>();
    int rate = 0;

    int id = 0;

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

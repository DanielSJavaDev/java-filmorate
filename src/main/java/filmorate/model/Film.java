package filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film {

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

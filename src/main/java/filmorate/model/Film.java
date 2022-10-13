package filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private final Set<Integer> liked = new HashSet<>();
    private int rate = 0;

    private Integer id = 0;

    @NotNull
    @NotBlank
    private String name;

    @Size(max = 200)
    private String description;

    @Past
    private LocalDate releaseDate;

    @Positive
    private long duration;

}

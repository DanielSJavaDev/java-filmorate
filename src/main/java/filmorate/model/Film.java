package filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Film {
    private Integer id = 0;

    @NotNull
    @NotBlank
    private String name;

    @Size(max = 200)
    private String description;

    @Past
    private LocalDate releaseDate;

    @Positive
    private Integer duration;

    private Mpa Mpa;

    private Integer likesCount;

    private Set<Genre> genres;

}

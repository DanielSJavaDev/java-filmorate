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
    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @Past
    private LocalDate releaseDate;

    @Size(max = 200)
    private String description;

    @Positive
    private Integer duration;

    private Integer likesCount;
    private Integer rate;
    private Mpa Mpa;
    private List<Genre> genres;
    private final Set<Integer> liked = new HashSet<>();

}

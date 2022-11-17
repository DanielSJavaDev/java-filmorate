package filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Genre {
    Integer id;
    String name;
}

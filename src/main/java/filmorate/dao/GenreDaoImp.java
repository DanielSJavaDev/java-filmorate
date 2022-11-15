package filmorate.dao;

import filmorate.exception.ParameterNotFoundException;
import filmorate.model.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenreDaoImp implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Genre> getAll() {
        String sqlQuery = "select * from genres;";
        List<Genre> genres =  new ArrayList<>();

        genres.addAll(jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeGenre(rs)));
        return genres;
    }

    @Override
    public Genre getGenreById(Integer genreId) {
        validateGenreId(genreId);
        return getAll().stream()
                .filter(genre -> Objects.equals(genre.getId(), genreId))
                .collect(Collectors.toList())
                .get(0);
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        return Genre.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }

    private void validateGenreId(Integer genreId){
        String sqlQuery = "select count(*) from genres where id = ?;";
        boolean isValid = jdbcTemplate.queryForObject(sqlQuery, Integer.class, genreId) != 0 && genreId > 0;
        if (!isValid) throw new ParameterNotFoundException("Can't find " + genreId);
    }
}
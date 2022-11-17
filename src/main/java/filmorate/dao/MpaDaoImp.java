package filmorate.dao;

import filmorate.exception.ParameterNotFoundException;
import filmorate.model.Mpa;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDaoImp implements MpaDao {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Mpa> getAllMpa() {
        String sqlQuery = "select * from mpa;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> createMpa(rs));
    }

    @Override
    public Mpa getMpaByRatingId(Integer ratingId) {
        validateMpa(ratingId);
        String sqlQuery = "select * from mpa where id = ?;";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> createMpa(rs), ratingId);
    }


    private Mpa createMpa(ResultSet rs) throws SQLException {
        return Mpa.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }

    private void validateMpa(Integer id) throws ParameterNotFoundException {
        String sqlQuery = "select count(*) from mpa where id = ?;";
        boolean isValid = jdbcTemplate.queryForObject(sqlQuery, Integer.class, id) == 1;
        if (!isValid) throw new ParameterNotFoundException("id " + id + " not found");
    }
}
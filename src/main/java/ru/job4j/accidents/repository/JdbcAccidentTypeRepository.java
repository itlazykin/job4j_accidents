package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class JdbcAccidentTypeRepository implements AccidentTypeRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<AccidentType> rowMapperAccidentType = (resultSet, rowNum) -> {
        AccidentType type = new AccidentType();
        type.setId(resultSet.getInt("id"));
        type.setName(resultSet.getString("type_name"));
        return type;
    };

    @Override
    public Collection<AccidentType> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM types", rowMapperAccidentType);
    }

    @Override
    public AccidentType findById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM types WHERE id = ?", rowMapperAccidentType, id);
    }
}

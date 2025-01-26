package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JdbcAccidentRepository implements AccidentRepository {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcAccidentRepository.class.getName());

    private final JdbcTemplate jdbcTemplate;

    private Accident newAccident(ResultSet resultSet) throws SQLException {
        Accident newAccident = new Accident();
        newAccident.setId(resultSet.getInt("id"));
        newAccident.setName(resultSet.getString("name"));
        newAccident.setText(resultSet.getString("text"));
        newAccident.setAddress(resultSet.getString("address"));
        return newAccident;
    }

    @Override
    public Optional<Accident> save(Accident accident) {
        try {
            String sql = "INSERT INTO accidents(name, text, address, type_id) VALUES (?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, accident.getName());
                pst.setString(2, accident.getText());
                pst.setString(3, accident.getAddress());
                pst.setInt(4, accident.getType().getId());
                return pst;
            }, keyHolder);
            accident.setId((int) keyHolder.getKeys().get("id"));
            for (Rule rule : accident.getRules()) {
                jdbcTemplate.update(
                        "INSERT INTO accidents_rules(accident_id, rule_id) VALUES (?, ?)",
                        accident.getId(), rule.getId());
            }
            return Optional.of(accident);
        } catch (Exception e) {
            LOG.info("Неудачная попытка сохранения инцидента, Exception in log example", e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Accident> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, text, address FROM accidents a ORDER BY a.id",
                (resultSet, rowNum) -> newAccident(resultSet));
    }

    @Override
    public Optional<Accident> findById(int id) {
        Accident accident = jdbcTemplate.queryForObject(
                "SELECT * FROM accidents a LEFT JOIN types t ON a.type_id = t.id WHERE a.id = ?",
                (resultSet, rowNum) -> {
                    Accident newAccident = newAccident(resultSet);
                    var type = new AccidentType();
                    type.setId(resultSet.getInt("type_id"));
                    type.setName(resultSet.getString("type_name"));
                    newAccident.setType(type);
                    return newAccident;
                }, id);
        if (accident == null) {
            return Optional.empty();
        }
        List<Rule> rules = jdbcTemplate.query(
                "SELECT * FROM rules r WHERE r.id in (SELECT rule_id FROM accidents_rules WHERE accident_id = ?)",
                (resultSet, rowNum) -> {
                    Rule rule = new Rule();
                    rule.setId(resultSet.getInt("id"));
                    rule.setName(resultSet.getString("rule_name"));
                    return rule;
                }, id);
        accident.getRules().addAll(rules);
        return Optional.of(accident);
    }

    @Override
    public boolean update(Accident accident) {
        jdbcTemplate.update(
                "DELETE FROM accidents_rules WHERE accident_id = ?", accident.getId());
        for (Rule rule : accident.getRules()) {
            jdbcTemplate.update(
                    "INSERT INTO accidents_rules(accident_id, rule_id) VALUES (?, ?)",
                    accident.getId(), rule.getId());
        }
        return jdbcTemplate.update(
                "UPDATE accidents set name = ?, text = ?, address = ?, type_id = ? where id = ?",
                accident.getName(), accident.getText(),
                accident.getAddress(), accident.getType().getId(), accident.getId()) > 0;
    }
}

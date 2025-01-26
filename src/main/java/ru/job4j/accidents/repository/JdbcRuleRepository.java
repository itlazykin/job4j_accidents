package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class JdbcRuleRepository implements RuleRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Rule> rowMapperRule = (rs, rowNum) -> {
        Rule rule = new Rule();
        rule.setId(rs.getInt("id"));
        rule.setName(rs.getString("rule_name"));
        return rule;
    };

    @Override
    public Collection<Rule> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM rules", rowMapperRule);
    }

    @Override
    public Collection<Rule> findAllById(Collection<Integer> rulesId) {
        return rulesId.stream()
                .map(id ->
                        jdbcTemplate.queryForObject("SELECT * FROM rules WHERE id = ?", rowMapperRule, id))
                .toList();
    }
}

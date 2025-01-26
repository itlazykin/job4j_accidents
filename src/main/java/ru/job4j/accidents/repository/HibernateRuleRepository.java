package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernateRuleRepository implements RuleRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Rule> findAll() {
        return crudRepository.query(
                "FROM Rule", Rule.class);
    }

    @Override
    public Collection<Rule> findAllById(Collection<Integer> rulesId) {
        return crudRepository.query(
                "from Rule where id in :fRulesId",
                Rule.class,
                Map.of("fRulesId", rulesId)
        );
    }
}

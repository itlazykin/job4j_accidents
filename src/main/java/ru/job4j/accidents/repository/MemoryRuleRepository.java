package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryRuleRepository implements RuleRepository {

    private final ConcurrentHashMap<Integer, Rule> types = new ConcurrentHashMap<>();

    public MemoryRuleRepository() {
        types.put(1, new Rule(1, "Статья 1"));
        types.put(2, new Rule(2, "Статья 2"));
        types.put(3, new Rule(3, "Статья 3"));
    }

    @Override
    public Collection<Rule> findAll() {
        return types.values();
    }

    @Override
    public Collection<Rule> findAllById(Collection<Integer> rulesId) {
        return rulesId.stream().map(types::get).toList();
    }
}

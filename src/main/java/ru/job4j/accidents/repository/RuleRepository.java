package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;

public interface RuleRepository {

    Collection<Rule> findAll();

    Collection<Rule> findAllById(Collection<Integer> rulesId);
}

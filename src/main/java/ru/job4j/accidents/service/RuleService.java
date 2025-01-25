package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;

public interface RuleService {

    Collection<Rule> findAll();

    Collection<Rule> findAllById(Collection<Integer> rulesId);
}

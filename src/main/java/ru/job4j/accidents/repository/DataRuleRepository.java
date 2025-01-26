package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;

public interface DataRuleRepository extends CrudRepository<Rule, Integer> {

    @Override
    Collection<Rule> findAll();

    @Override
    Collection<Rule> findAllById(Iterable<Integer> integers);
}
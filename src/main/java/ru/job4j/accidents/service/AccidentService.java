package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface AccidentService {

    Accident save(Accident accident);

    Collection<Accident> findAll();

    Optional<Accident> findById(int id);

    boolean update(Accident accident);
}

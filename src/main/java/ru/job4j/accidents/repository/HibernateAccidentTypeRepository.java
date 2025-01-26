package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernateAccidentTypeRepository implements AccidentTypeRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<AccidentType> findAll() {
        return crudRepository.query(
                "FROM AccidentType", AccidentType.class);
    }

    @Override
    public AccidentType findById(int id) {
        return crudRepository.getSingleResult(
                "FROM AccidentType WHERE id = :fId",
                AccidentType.class,
                Map.of("fId", id));
    }
}

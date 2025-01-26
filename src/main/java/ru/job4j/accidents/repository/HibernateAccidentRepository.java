package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateAccidentRepository implements AccidentRepository {

    private final CrudRepository crudRepository;

    private static final Logger LOG = LoggerFactory.getLogger(HibernateAccidentRepository.class.getName());

    @Override
    public Optional<Accident> save(Accident accident) {
        try {
            crudRepository.run(session -> session.persist(accident));
            return Optional.of(accident);
        } catch (Exception e) {
            LOG.info("Неудачная попытка сохранения инцидента, Exception in log example", e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Accident> findAll() {
        return crudRepository.query(
                "FROM Accident ORDER BY id", Accident.class);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return crudRepository.optional(
                "FROM Accident a LEFT JOIN FETCH a.rules WHERE a.id = :fId", Accident.class,
                Map.of("fId", id)
        );
    }

    @Override
    public boolean update(Accident accident) {
        try {
            crudRepository.run(session -> session.merge(accident));
            return true;
        } catch (Exception e) {
            LOG.info("Неудачная попытка обновления инцидента, Exception in log example", e);
        }
        return false;
    }
}

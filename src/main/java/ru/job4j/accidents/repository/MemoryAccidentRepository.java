package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemoryAccidentRepository implements AccidentRepository {

    private final ConcurrentHashMap<Integer, Accident> accidents = new ConcurrentHashMap<>();

    private final AtomicInteger nextId = new AtomicInteger();

    public MemoryAccidentRepository() {
        var rulesOne = Set.of(new Rule(1, "Статья 1"));
        var rulesTwo = Set.of(new Rule(1, "Статья 1"), new Rule(2, "Статья 2"));
        var rulesThree = Set.of(new Rule(2, "Статья 2"), new Rule(3, "Статья 3"));
        save(new Accident(0, "Превышение скорости", "Превышение на 30 км/ч", "Высоцкого 5",
                new AccidentType(1, "Две машины"), rulesOne));
        save(new Accident(0, "Выезд за стоп линию", "продолжил движение", "Армейская 22",
                new AccidentType(2, "Машина и человек"), rulesTwo));
        save(new Accident(0, "ДТП", "не пропустил с прилегающей территории", "Восточная 82",
                new AccidentType(3, "Машина и велосипед"), rulesThree));
    }

    @Override
    public Optional<Accident> save(Accident accident) {
        accident.setId(nextId.incrementAndGet());
        accidents.put(accident.getId(), accident);
        return Optional.ofNullable(accidents.put(accident.getId(), accident));
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(),
                (id, oldAccident) -> new Accident(
                        oldAccident.getId(),
                        oldAccident.getName(),
                        oldAccident.getText(),
                        oldAccident.getAddress(),
                        oldAccident.getType(),
                        oldAccident.getRules()
                )) != null;
    }
}

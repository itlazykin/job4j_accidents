package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemoryAccidentRepository implements AccidentRepository {

    private final ConcurrentHashMap<Integer, Accident> accidents = new ConcurrentHashMap<>();

    private final AtomicInteger nextId = new AtomicInteger();

    public MemoryAccidentRepository() {
        save(new Accident(0, "Превышение скорости", "Превышение на 30 км/ч", "Высоцкого 5"));
        save(new Accident(0, "Выезд за стоп линию", "продолжил движение", "Армейская 22"));
        save(new Accident(0, "ДТП", "не пропустил с прилегающей территории", "Восточная 82"));
    }

    @Override
    public Accident save(Accident accident) {
        accident.setId(nextId.incrementAndGet());
        accidents.put(accident.getId(), accident);
        return accident;
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
                        oldAccident.getAddress()
                )) != null;
    }
}

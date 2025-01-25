package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryAccidentTypeRepository implements AccidentTypeRepository {

    private final ConcurrentHashMap<Integer, AccidentType> types = new ConcurrentHashMap<>();

    public MemoryAccidentTypeRepository() {
        types.put(1, new AccidentType(1, "Две машины"));
        types.put(2, new AccidentType(2, "Машина и человек"));
        types.put(3, new AccidentType(3, "Машина и велосипед"));
    }

    @Override
    public Collection<AccidentType> findAll() {
        return types.values();
    }

    @Override
    public AccidentType findById(int id) {
        return types.get(id);
    }
}

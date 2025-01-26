package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;;
import ru.job4j.accidents.repository.DataAccidentTypeRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class SimpleAccidentTypeService implements AccidentTypeService {

    private final DataAccidentTypeRepository dataAccidentTypeRepository;

    @Override
    public Collection<AccidentType> findALl() {
        return dataAccidentTypeRepository.findAll();
    }

    @Override
    public AccidentType findById(int id) {
        return dataAccidentTypeRepository.findById(id).get();
    }
}

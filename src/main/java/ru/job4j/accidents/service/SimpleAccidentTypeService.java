package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class SimpleAccidentTypeService implements AccidentTypeService {

    private final AccidentTypeRepository accidentTypeRepository;

    @Override
    public Collection<AccidentType> findALl() {
        return accidentTypeRepository.findAll();
    }

    @Override
    public AccidentType findById(int id) {
        return accidentTypeRepository.findById(id);
    }
}

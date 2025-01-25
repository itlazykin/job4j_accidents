package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleAccidentsService implements AccidentService {

    private final AccidentRepository accidentRepository;

    private final AccidentTypeService accidentTypeService;

    private void setAccidentType(Accident accident) {
        accident.setType(accidentTypeService.findById(accident.getType().getId()));
    }

    @Override
    public Accident save(Accident accident) {
        setAccidentType(accident);
        return accidentRepository.save(accident);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public boolean update(Accident accident) {
        return accidentRepository.update(accident);
    }
}

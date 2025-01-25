package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleAccidentsService implements AccidentService {

    private final AccidentRepository accidentRepository;

    private final AccidentTypeService accidentTypeService;

    private final RuleService ruleService;

    private void setAccidentTypeAndRule(Accident accident, List<Integer> rulesId) {
        accident.setType(accidentTypeService.findById(accident.getType().getId()));
        accident.getRules().addAll(ruleService.findAllById(rulesId));
    }

    @Override
    public Accident save(Accident accident, List<Integer> rulesId) {
        setAccidentTypeAndRule(accident, rulesId);
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
    public boolean update(Accident accident, List<Integer> rulesId) {
        setAccidentTypeAndRule(accident, rulesId);
        return accidentRepository.update(accident);
    }
}

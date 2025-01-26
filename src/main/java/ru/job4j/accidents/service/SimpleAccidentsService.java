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

    private final AccidentRepository jdbcAccidentRepository;

    private final AccidentTypeService jdbcAccidentTypeRepository;

    private final RuleService jdbcRuleRepository;

    private void setAccidentTypeAndRule(Accident accident, List<Integer> rulesId) {
        accident.setType(jdbcAccidentTypeRepository.findById(accident.getType().getId()));
        accident.getRules().addAll(jdbcRuleRepository.findAllById(rulesId));
    }

    @Override
    public Optional<Accident> save(Accident accident, List<Integer> rulesId) {
        setAccidentTypeAndRule(accident, rulesId);
        return jdbcAccidentRepository.save(accident);
    }

    @Override
    public Collection<Accident> findAll() {
        return jdbcAccidentRepository.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return jdbcAccidentRepository.findById(id);
    }

    @Override
    public boolean update(Accident accident, List<Integer> rulesId) {
        setAccidentTypeAndRule(accident, rulesId);
        return jdbcAccidentRepository.update(accident);
    }
}

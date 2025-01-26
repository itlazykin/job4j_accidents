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

    private final AccidentRepository hibernateAccidentRepository;

    private final AccidentTypeService hibernateAccidentTypeRepository;

    private final RuleService hibernateRuleRepository;

    private void setAccidentTypeAndRule(Accident accident, List<Integer> rulesId) {
        accident.setType(hibernateAccidentTypeRepository.findById(accident.getType().getId()));
        accident.getRules().addAll(hibernateRuleRepository.findAllById(rulesId));
    }

    @Override
    public Optional<Accident> save(Accident accident, List<Integer> rulesId) {
        setAccidentTypeAndRule(accident, rulesId);
        return hibernateAccidentRepository.save(accident);
    }

    @Override
    public Collection<Accident> findAll() {
        return hibernateAccidentRepository.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return hibernateAccidentRepository.findById(id);
    }

    @Override
    public boolean update(Accident accident, List<Integer> rulesId) {
        setAccidentTypeAndRule(accident, rulesId);
        return hibernateAccidentRepository.update(accident);
    }
}

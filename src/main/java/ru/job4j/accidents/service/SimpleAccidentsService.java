package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.DataAccidentRepository;
import ru.job4j.accidents.repository.HibernateAccidentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentsService implements AccidentService {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateAccidentRepository.class.getName());

    private final DataAccidentRepository dataAccidentRepository;

    private final AccidentTypeService accidentTypeService;

    private final RuleService ruleService;

    private void setAccidentTypeAndRule(Accident accident, List<Integer> rulesId) {
        accident.setType(accidentTypeService.findById(accident.getType().getId()));
        accident.getRules().addAll(ruleService.findAllById(rulesId));
    }

    @Override
    public Optional<Accident> save(Accident accident, List<Integer> rulesId) {
        try {
            setAccidentTypeAndRule(accident, rulesId);
            return Optional.of(dataAccidentRepository.save(accident));
        } catch (Exception e) {
            LOG.info("Неудачная попытка сохранения/изменения инцидента, Exception in log example", e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Accident> findAll() {
        return dataAccidentRepository.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return dataAccidentRepository.findById(id);
    }
}

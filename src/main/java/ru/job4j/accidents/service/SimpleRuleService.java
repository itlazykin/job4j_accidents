package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.DataRuleRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class SimpleRuleService implements RuleService {

    private final DataRuleRepository dataRuleRepository;

    @Override
    public Collection<Rule> findAll() {
        return dataRuleRepository.findAll();
    }

    @Override
    public Collection<Rule> findAllById(Collection<Integer> rulesId) {
        return dataRuleRepository.findAllById(rulesId);
    }
}

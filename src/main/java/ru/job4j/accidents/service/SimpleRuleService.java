package ru.job4j.accidents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class SimpleRuleService implements RuleService {

    private final RuleRepository hibernateRuleRepository;

    @Override
    public Collection<Rule> findAll() {
        return hibernateRuleRepository.findAll();
    }

    @Override
    public Collection<Rule> findAllById(Collection<Integer> rulesId) {
        return hibernateRuleRepository.findAllById(rulesId);
    }
}

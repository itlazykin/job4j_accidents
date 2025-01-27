package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {

    private final AccidentService accidentService;

    private final AccidentTypeService accidentTypeService;

    private final RuleService ruleService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "accidents/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("types", accidentTypeService.findALl());
        model.addAttribute("rules", ruleService.findAll());
        return "accidents/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Accident accident, @RequestParam List<Integer> rulesId, Model model) {
        var isUpdated = accidentService.save(accident, rulesId);
        if (isUpdated.isEmpty()) {
            model.addAttribute("message", "Неудачная попытка сохранения/изменения инцидента");
            return "errors/404";
        }
        return "redirect:/accidents";
    }

    @GetMapping("/formUpdateAccident")
    public String getById(@RequestParam("id") int id, Model model) {
        var accidentOptional = accidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Инцидент с указанным идентификатором не найден");
            return "errors/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        model.addAttribute("types", accidentTypeService.findALl());
        model.addAttribute("rules", ruleService.findAll());
        return "accidents/edit";
    }
}
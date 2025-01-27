package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.AuthorityRepository;
import ru.job4j.accidents.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleUserService.class.getName());
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public Optional<User> save(User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorityRepository.findByAuthority("ROLE_USER"));
        try {
            userRepository.save(user);
            return Optional.of(user);
        } catch (Exception e) {
            LOG.info("Регистрация зарегистрированного пользователя, Exception in log example", e);
        }
        return Optional.empty();
    }
}
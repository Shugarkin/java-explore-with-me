package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.dao.UserMainServiceRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {


    private final UserMainServiceRepository repository;

    @Transactional
    @Override
    public User createUser(User user) {
        log.info("create user");
        return repository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(long userId) {
        boolean answer = repository.existsById(userId);
        if (answer) {
            log.info("delete user");
            repository.deleteById(userId);
        } else {
            throw new NotFoundException("Пользователя с данным id нет");
        }
    }

    @Override
    public List<User> getUsers(List<Long> ids, int from, int size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size, Sort.by("id").ascending());

        if (ids.isEmpty()) {
            log.info("get all user");
            return repository.findAllUser(pageable);
        }
        List<User> allByIds = repository.findAllByIds(ids, pageable);
        if (allByIds.isEmpty()) {
            return List.of();
        }
        log.info("get list user");
        return allByIds;
    }
}

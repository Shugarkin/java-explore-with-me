package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.UserMainServiceRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {


    private final UserMainServiceRepository repository;

    @Transactional
    @Override
    public User createUser(User user) {
        return repository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(long userId) {
        boolean answer = repository.existsById(userId);
        if (answer) {
            repository.deleteById(userId);
        } else {
            throw new NotFoundException("Пользователя с данным id нет");
        }
    }

    @Override
    public List<User> getUsers(List<Long> ids, int from, int size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size, Sort.by("id").descending());

        if (ids.isEmpty()) {
            return repository.findAllUser(pageable);
        }
        List<User> allByIds = repository.findAllByIds(ids, pageable);
        if (allByIds.isEmpty()) {
            return List.of();
        }
        return allByIds;
    }
}

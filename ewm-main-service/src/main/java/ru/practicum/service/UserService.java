package ru.practicum.service;

import ru.practicum.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    void deleteUser(long userId);

    List<User> getUsers(List<Long> ids, int from, int size);
}

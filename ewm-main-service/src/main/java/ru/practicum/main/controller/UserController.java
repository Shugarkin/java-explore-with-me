package ru.practicum.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.AdminUserDto;
import ru.practicum.dto.Marker;
import ru.practicum.dto.UserDtoReceived;
import ru.practicum.main.mapper.UserMapper;
import ru.practicum.main.model.User;
import ru.practicum.main.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdminUserDto createUser(@RequestBody @Validated({Marker.Create.class}) UserDtoReceived userDto) {
        User newUser = userService.createUser(UserMapper.toUser(userDto));
        return UserMapper.toAdminUserDto(newUser);
    }


    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping
    public List<AdminUserDto> getUsers(@RequestParam(defaultValue = "") List<Long> ids, @RequestParam(defaultValue = "0") int from,
                                  @RequestParam(defaultValue = "10") int size) {
        List<AdminUserDto> list = UserMapper.toListAdminUserDto(userService.getUsers(ids, from, size));
        return list;
    }

}

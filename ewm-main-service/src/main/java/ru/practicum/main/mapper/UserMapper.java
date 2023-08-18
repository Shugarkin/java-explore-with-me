package ru.practicum.main.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.dto.AdminUserDto;
import ru.practicum.main.dto.UserDto;
import ru.practicum.main.dto.UserDtoReceived;
import ru.practicum.main.model.User;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public User toUser(UserDtoReceived userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .id(user.getId())
                .build();
    }

    public AdminUserDto toAdminUserDto(User newUser) {
        return AdminUserDto.builder()
                .email(newUser.getEmail())
                .id(newUser.getId())
                .name(newUser.getName())
                .build();
    }

    public List<AdminUserDto> toListAdminUserDto(List<User> users) {
        return users.stream().map(UserMapper::toAdminUserDto).collect(Collectors.toList());
    }
}

package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.UserDto;
import ru.practicum.dto.UserDtoReceived;
import ru.practicum.model.User;

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

    public List<User> toListUserFromUserReceived(List<UserDtoReceived> list) {
        return list.stream().map(UserMapper::toUser).collect(Collectors.toList());
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .id(user.getId())
                .build();
    }

    public List<UserDto> toListUserDto(List<User> list) {
        return list.stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

}

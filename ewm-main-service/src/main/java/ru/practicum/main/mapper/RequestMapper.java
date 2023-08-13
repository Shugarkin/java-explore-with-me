package ru.practicum.main.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.RequestDto;
import ru.practicum.main.model.Request;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RequestMapper {

    public RequestDto toRequestDto(Request request) {
        return RequestDto.builder()
                .requester(request.getRequester().getId())
                .id(request.getId())
                .created(request.getCreated())
                .event(request.getEvent().getId())
                .status(request.getStatus())
                .build();
    }

    public static List<RequestDto> toListRequestDto(List<Request> requests) {
        return requests.stream().map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }
}

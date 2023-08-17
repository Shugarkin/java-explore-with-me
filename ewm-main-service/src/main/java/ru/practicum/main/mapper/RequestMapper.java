package ru.practicum.main.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.RequestDto;
import ru.practicum.dto.RequestShortDto;
import ru.practicum.dto.RequestShortUpdateDto;
import ru.practicum.main.model.Request;
import ru.practicum.main.model.RequestShort;
import ru.practicum.main.model.RequestShortUpdate;

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

    public List<RequestDto> toListRequestDto(List<Request> requests) {
        return requests.stream().map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }

    public RequestShort toRequestShort(RequestShortDto shortDto) {
        return RequestShort.builder()
                .requestIds(shortDto.getRequestIds())
                .status(shortDto.getStatus())
                .build();
    }

    public RequestShortUpdateDto toRequestShortUpdateDto(RequestShortUpdate requestShort) {
        return RequestShortUpdateDto.builder()
                .rejectedRequests(RequestMapper.toListRequestDto(requestShort.getCanselRequest()))
                .confirmedRequests(RequestMapper.toListRequestDto(requestShort.getConformedRequest()))
                .build();
    }

}

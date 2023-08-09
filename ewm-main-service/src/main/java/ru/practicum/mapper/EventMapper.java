package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.*;
import ru.practicum.model.Categories;
import ru.practicum.model.Event;
import ru.practicum.model.EventFull;

@UtilityClass
public class EventMapper {

    public Event toEvent(EventReceivedDto receivedDto) {
        return Event.builder()
                .eventDate(receivedDto.getEventDate())
                .annotation(receivedDto.getAnnotation())
                .category(Categories.builder().id(receivedDto.getCategory()).build())
                .paid(receivedDto.isPaid())
                .description(receivedDto.getDescription())
                .title(receivedDto.getTitle())
                .participantLimit(receivedDto.getParticipantLimit())
                .requestModeration(receivedDto.isRequestModeration())
                .location(LocationMapper.toLocation(receivedDto.getLocation()))
                .build();
    }

    public EventFull toEventFull(Event event, Long view, Long confirmedRequests) {
        return EventFull.builder()
                .createdOn(event.getCreatedOn())
                .initiator(event.getInitiator())
                .confirmedRequests(confirmedRequests)
                .views(view)
                .state(event.getState())
                .annotation(event.getAnnotation())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.isRequestModeration())
                .paid(event.isPaid())
                .title(event.getTitle())
                .id(event.getId())
                .category(event.getCategory())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .location(event.getLocation())
                .build();
    }

    public EventFullDto toEventFullDto(EventFull event) {
        return EventFullDto.builder()
                .createdOn(event.getCreatedOn())
                .initiator(UserDto.builder().name(event.getInitiator().getName()).id(event.getInitiator().getId()).build())
                .confirmedRequests(event.getConfirmedRequests())
                .views(event.getViews())
                .state(event.getState())
                .annotation(event.getAnnotation())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.isRequestModeration())
                .paid(event.isPaid())
                .title(event.getTitle())
                .id(event.getId())
                .category(CategoriesDto.builder().id(event.getCategory().getId()).name(event.getCategory().getName()).build())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .location(LocationDto.builder().lon(event.getLocation().getLon()).lat(event.getLocation().getLat()).build())
                .build();
    }
}

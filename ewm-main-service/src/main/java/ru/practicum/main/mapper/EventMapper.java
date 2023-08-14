package ru.practicum.main.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.*;
import ru.practicum.main.model.*;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class EventMapper {

    public Event toEvent(EventReceivedDto receivedDto) {
        return Event.builder()
                .eventDate(receivedDto.getEventDate())
                .annotation(receivedDto.getAnnotation())
                .category(Categories.builder().id(receivedDto.getCategory()).build())
                .paid(receivedDto.getPaid())
                .description(receivedDto.getDescription())
                .title(receivedDto.getTitle())
                .participantLimit(receivedDto.getParticipantLimit())
                .requestModeration(receivedDto.getRequestModeration())
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
                .requestModeration(event.getRequestModeration())
                .paid(event.getPaid())
                .title(event.getTitle())
                .id(event.getId())
                .category(event.getCategory())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .location(event.getLocation())
                .publishedOn(event.getPublishedOn())
                .build();
    }

    public EventFullDto toEventFullDto(EventFull event) {
        return EventFullDto.builder()
                .createdOn(event.getCreatedOn())
                .initiator(UserMapper.toUserDto(event.getInitiator()))
                .confirmedRequests(event.getConfirmedRequests())
                .views(event.getViews())
                .state(event.getState())
                .annotation(event.getAnnotation())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.isRequestModeration())
                .paid(event.isPaid())
                .title(event.getTitle())
                .id(event.getId())
                .category(CategoriesMapper.toCategoriesDto(event.getCategory()))
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .publishedOn(event.getPublishedOn())
                .build();
    }

    public List<EventFullDto> toListEventFullDto(List<EventFull> list) {
        return list.stream().map(EventMapper::toEventFullDto).collect(Collectors.toList());
    }

    public UpdateEvent toEventFromUpdateEvent(UpdateEventDto updateEventDto) {
        return UpdateEvent.builder()
                .stateAction(updateEventDto.getStateAction())
                .annotation(updateEventDto.getAnnotation())
                .participantLimit(updateEventDto.getParticipantLimit())
                .requestModeration(updateEventDto.isRequestModeration())
                .category(updateEventDto.getCategory())
                .description(updateEventDto.getDescription())
                .title(updateEventDto.getTitle())
                .paid(updateEventDto.getPaid())
                .eventDate(updateEventDto.getEventDate())
                .location(updateEventDto.getLocation())
                .build();
    }

    public static AdminEvent toAdminEventFromAdminDto(AdminEventReceivedDto adminEvent) {
        AdminEvent event = AdminEvent.builder()
                .stateAction(adminEvent.getStateAction())
                .annotation(adminEvent.getAnnotation())
                .participantLimit(adminEvent.getParticipantLimit())
                .requestModeration(adminEvent.getRequestModeration())
                .category(adminEvent.getCategory())
                .description(adminEvent.getDescription())
                .eventDate(adminEvent.getEventDate())
                .paid(adminEvent.getPaid())
                .title(adminEvent.getTitle())
                .build();
        if (adminEvent.getLocation() == null) {
            event.setLocation(null);
        } else {
            event.setLocation(LocationMapper.toLocation(adminEvent.getLocation()));
        }
        return event;
    }

    public EventShort toEventShort(Event event, Long view, Long confirmedRequests) {
        return EventShort.builder()
                .id(event.getId())
                .eventDate(event.getEventDate())
                .confirmedRequests(confirmedRequests)
                .views(view)
                .annotation(event.getAnnotation())
                .category(event.getCategory())
                .initiator(event.getInitiator())
                .paid(event.getPaid())
                .title(event.getTitle())
                .build();
    }

    public EventShortDto toEventShortDto(EventShort eventShort) {
        return EventShortDto.builder()
                .initiator(UserMapper.toUserDto(eventShort.getInitiator()))
                .views(eventShort.getViews())
                .eventDate(eventShort.getEventDate())
                .annotation(eventShort.getAnnotation())
                .title(eventShort.getTitle())
                .category(CategoriesMapper.toCategoriesDto(eventShort.getCategory()))
                .confirmedRequests(eventShort.getConfirmedRequests())
                .id(eventShort.getId())
                .paid(eventShort.getPaid())
                .build();
    }

    public static List<EventShortDto> toListEventShortDto(List<EventShort> list) {
        return list.stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }
}

package ru.practicum.main.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.dto.*;
import ru.practicum.main.model.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class EventMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

    public EventFullDto toEventFullDto(Event event) {
        EventFullDto build = EventFullDto.builder()
                .createdOn(event.getCreatedOn().format(FORMATTER))
                .initiator(UserMapper.toUserDto(event.getInitiator()))
                .confirmedRequests(event.getConfirmedRequests())
                .views(event.getView())
                .state(event.getState())
                .annotation(event.getAnnotation())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .paid(event.getPaid())
                .title(event.getTitle())
                .id(event.getId())
                .category(CategoriesMapper.toCategoriesDto(event.getCategory()))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(FORMATTER))
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .build();

        if (event.getPublishedOn() != null) {
            build.setPublishedOn(event.getPublishedOn().format(FORMATTER));
        }
        return build;
    }

    public List<EventFullDto> toListEventFullDto(List<Event> list) {
        return list.stream().map(EventMapper::toEventFullDto).collect(Collectors.toList());
    }

    public UpdateEvent toEventFromUpdateEvent(UpdateEventDto updateEventDto) {
        UpdateEvent event = UpdateEvent.builder()
                .stateAction(updateEventDto.getStateAction())
                .annotation(updateEventDto.getAnnotation())
                .participantLimit(updateEventDto.getParticipantLimit())
                .requestModeration(updateEventDto.getRequestModeration())
                .category(updateEventDto.getCategory())
                .description(updateEventDto.getDescription())
                .title(updateEventDto.getTitle())
                .paid(updateEventDto.getPaid())
                .eventDate(updateEventDto.getEventDate())
                .build();
        if (updateEventDto.getLocation() == null) {
            event.setLocation(null);
        } else {
            event.setLocation(LocationMapper.toLocation(updateEventDto.getLocation()));
        }
        return event;
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
                .eventDate(eventShort.getEventDate().format(FORMATTER))
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

    public EventShort toEventShort(Event event) {
        return EventShort.builder()
                .id(event.getId())
                .eventDate(event.getEventDate())
                .confirmedRequests(event.getConfirmedRequests())
                .views(event.getView())
                .annotation(event.getAnnotation())
                .category(event.getCategory())
                .initiator(event.getInitiator())
                .paid(event.getPaid())
                .title(event.getTitle())
                .build();
    }

    public static List<EventShort> toListEventShort(List<Event> list) {
        return list.stream().map(EventMapper::toEventShort).collect(Collectors.toList());
    }

    public EventCommentDto  toEventComment(Event event) {
        return EventCommentDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .build();
    }

    public EventFullWithComment toEventWithComment(Event event, List<Comment> commentList) {
        EventFullWithComment build = EventFullWithComment.builder()
                .createdOn(event.getCreatedOn().format(FORMATTER))
                .initiator(event.getInitiator())
                .confirmedRequests(event.getConfirmedRequests())
                .views(event.getView())
                .state(event.getState())
                .annotation(event.getAnnotation())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .paid(event.getPaid())
                .title(event.getTitle())
                .id(event.getId())
                .category(event.getCategory())
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(FORMATTER))
                .location(event.getLocation())
                .comments(commentList)
                .build();

        if (event.getPublishedOn() != null) {
            build.setPublishedOn(event.getPublishedOn().format(FORMATTER));
        }
        return build;
    }

    public EventFullWithCommentDto toEventWIthCommentDto(EventFullWithComment event) {
        return EventFullWithCommentDto.builder()
                .createdOn(event.getCreatedOn())
                .initiator(UserMapper.toUserDto(event.getInitiator()))
                .confirmedRequests(event.getConfirmedRequests())
                .views(event.getViews())
                .state(event.getState())
                .annotation(event.getAnnotation())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .paid(event.getPaid())
                .title(event.getTitle())
                .id(event.getId())
                .category(CategoriesMapper.toCategoriesDto(event.getCategory()))
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .comments(CommentMapper.toListCommentShortDto(event.getComments()))
                .build();
    }
}

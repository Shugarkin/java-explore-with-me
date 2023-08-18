package ru.practicum.main.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.main.dto.State;
import ru.practicum.dto.Status;
import ru.practicum.main.model.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RequestMainServiceRepositoryTest {

    @Autowired
    private RequestMainServiceRepository repository;

    @Autowired
    private UserMainServiceRepository userMainServiceRepository;

    @Autowired
    private EventMainServiceRepository eventMainServiceRepository;

    @Autowired
    private LocationMainServiceRepository locationMainServiceRepository;

    @Autowired
    private CategoriesMainServiceRepository categoriesMainServiceRepository;

    private long locationId = 1L;
    private Location location = Location.builder().lat("1124").lon("1421").build();

    private long categoriesId = 1L;
    private Categories categories = Categories.builder().name("asdsa").build();

    private long userId = 1L;
    private User user = User.builder().id(1L).name("asdasf").email("asdsf@asfs.ru").build();

    private long eventId = 1L;
    private Event event = Event.builder()
            .id(1L)
            .eventDate(LocalDateTime.now().withNano(0).plusDays(1))
            .description("aaaaaaaaaaaaaaaaaaaaaaaa")
            .title("aaaaaaaaa")
            .location(location)
            .paid(false)
            .category(categories)
            .participantLimit(12)
            .createdOn(LocalDateTime.now().withNano(0))
            .requestModeration(false)
            .annotation("aaaaaaaaaaaaaaaaaaaaaaaaa")
            .initiator(user)
            .state(State.PUBLISHED)
            .publishedOn(LocalDateTime.now().withNano(0))
            .build();

    private long requestId = 1L;
    private Request request = Request.builder()
            .created(LocalDateTime.now().withNano(0))
            .requester(user)
            .event(event)
            .status(Status.CONFIRMED)
            .build();


    @BeforeEach
    void before() {
        user = userMainServiceRepository.save(user);
        userId = user.getId();

        location = locationMainServiceRepository.save(location);
        locationId = location.getId();

        categories = categoriesMainServiceRepository.save(categories);
        categoriesId = categories.getId();

        event.setInitiator(user);
        event.setLocation(location);
        event.setCategory(categories);
        event = eventMainServiceRepository.save(event);
        eventId = event.getId();

        request.setRequester(user);
        request.setEvent(event);
        request = repository.save(request);
        requestId = request.getId();
    }

    @AfterEach
    void after() {
        userMainServiceRepository.deleteAll();
        locationMainServiceRepository.deleteAll();
        categoriesMainServiceRepository.deleteAll();
        eventMainServiceRepository.deleteAll();
        repository.deleteAll();
    }


    @Test
    void countByEventId() {
        List<ConfirmedRequestShort> confirmedRequestShorts = repository.countByEventId(List.of(eventId));

        assertEquals(confirmedRequestShorts.size(), 1);
    }

    @Test
    void existsByRequesterIdAndEventId() {
        boolean b = repository.existsByRequesterIdAndEventId(userId, eventId);

        assertEquals(b, true);
    }

    @Test
    void findAllByRequesterId() {
        List<Request> allByRequesterId = repository.findAllByRequesterId(userId);
        assertEquals(allByRequesterId.size(), 1);
    }

    @Test
    void findAllByEventId() {
        List<Request> allByEventId = repository.findAllByEventId(eventId);
        assertEquals(allByEventId.size(), 1);
    }
}
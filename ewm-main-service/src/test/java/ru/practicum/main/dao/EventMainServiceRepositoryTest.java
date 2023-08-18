//package ru.practicum.main.dao;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import ru.practicum.main.dto.State;
//import ru.practicum.main.model.Categories;
//import ru.practicum.main.model.Event;
//import ru.practicum.main.model.Location;
//import ru.practicum.main.model.User;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class EventMainServiceRepositoryTest {
//
//    @Autowired
//    private EventMainServiceRepository repository;
//
//    @Autowired
//    private UserMainServiceRepository userMainServiceRepository;
//
//    @Autowired
//    private LocationMainServiceRepository locationMainServiceRepository;
//
//    @Autowired
//    private CategoriesMainServiceRepository categoriesMainServiceRepository;
//
//    private long locationId = 1L;
//    private Location location = Location.builder().lat("1124").lon("1421").build();
//
//    private long categoriesId = 1L;
//    private Categories categories = Categories.builder().name("asdsa").build();
//
//    private long userId = 1L;
//    private User user = User.builder().id(1L).name("asdasf").email("asdsf@asfs.ru").build();
//
//    private long eventId = 1L;
//    private Event event = Event.builder()
//            .id(1L)
//            .eventDate(LocalDateTime.now().withNano(0).plusDays(1))
//            .description("aaaaaaaaaaaaaaaaaaaaaaaa")
//            .title("aaaaaaaaa")
//            .location(location)
//            .paid(false)
//            .category(categories)
//            .participantLimit(12)
//            .createdOn(LocalDateTime.now().withNano(0))
//            .requestModeration(false)
//            .annotation("aaaaaaaaaaaaaaaaaaaaaaaaa")
//            .initiator(user)
//            .state(State.PUBLISHED)
//            .publishedOn(LocalDateTime.now().withNano(0))
//            .build();
//
//    private Pageable pageable = PageRequest.of(0, 10);
//
//    @BeforeEach
//    void before() {
//        user = userMainServiceRepository.save(user);
//        userId = user.getId();
//
//        location = locationMainServiceRepository.save(location);
//        locationId = location.getId();
//
//        categories = categoriesMainServiceRepository.save(categories);
//        categoriesId = categories.getId();
//
//        event.setInitiator(user);
//        event.setLocation(location);
//        event.setCategory(categories);
//        event = repository.save(event);
//        eventId = event.getId();
//    }
//
//    @AfterEach
//    void after() {
//        userMainServiceRepository.deleteAll();
//        locationMainServiceRepository.deleteAll();
//        categoriesMainServiceRepository.deleteAll();
//        repository.deleteAll();
//    }
//
//    @Test
//    void findAllByInitiatorId() {
//
//        List<Event> allByInitiatorId = repository.findAllByInitiatorId(userId, pageable);
//
//        assertEquals(allByInitiatorId, List.of(event));
//    }
//
//    @Test
//    void findByIdAndInitiatorId() {
//
//        Optional<Event> byIdAndInitiatorId = repository.findByIdAndInitiatorId(event.getId(), user.getId());
//
//        assertNotNull(byIdAndInitiatorId.get());
//    }
//
//    @Test
//    void findAllByParam() {
//        List<Event> allByParam = repository.findAllByParam(List.of(userId), List.of(State.PUBLISHED), List.of(categoriesId), null, null, pageable);
//
//        assertEquals(1, allByParam.size());
//    }
//
//    @Test
//    void existsByIdAndInitiatorId() {
//        boolean b = repository.existsByIdAndInitiatorId(eventId, userId);
//        assertEquals(b, true);
//    }
//
//    @Test
//    void findAllEvents() {
//        List<Event> events = repository.findAllEvents("aaaaaaaaaaaaaaaaaaaaaaaaa", null, null, null, null, false, "id", pageable);
//        assertEquals(events.size(), 1);
//    }
//
//    @Test
//    void existsByCategoryId() {
//        boolean b = repository.existsByCategoryId(categoriesId);
//        assertEquals(b, true);
//    }
//}
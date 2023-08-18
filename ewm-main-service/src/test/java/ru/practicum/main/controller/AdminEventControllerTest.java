//package ru.practicum.main.controller;
//
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.practicum.main.dto.AdminEventReceivedDto;
//import ru.practicum.main.dto.AdminUpdateEventStatus;
//import ru.practicum.main.service.AdminEventService;
//
//import java.time.LocalDateTime;
//
//@ExtendWith(MockitoExtension.class)
//class AdminEventControllerTest {
//
//    @Mock
//    private AdminEventService service;
//
//    @InjectMocks
//    private AdminEventController controller;
//
//    private AdminEventReceivedDto adminEventReceivedDto = AdminEventReceivedDto.builder()
//            .category(1L)
//            .annotation("asdssssssssssssssssssssss")
//            .eventDate(LocalDateTime.now().plusDays(1).withNano(0))
//            .title("asdsa")
//            .description("sssssssssssssssssssssssss")
//            .paid(false)
//            .location(null)
//            .participantLimit(12)
//            .requestModeration(false)
//            .stateAction(AdminUpdateEventStatus.PUBLISH_EVENT)
//            .build();
//
////    private EventFull eventFull = EventFull.builder()
////            .annotation("asdssssssssssssssssssssss")
////            .eventDate(LocalDateTime.now().plusDays(1).withNano(0))
////            .title("asdsa")
////            .description("sssssssssssssssssssssssss")
////            .paid(false)
////            .location(Location.builder().id(1L).lat("123").lon("1342").build())
////            .category(Categories.builder().id(1).name("14312").build())
////            .initiator(User.builder().name("qew").id(1L).build())
////            .participantLimit(12)
////            .requestModeration(false)
////            .createdOn(LocalDateTime.now().plusDays(1).withNano(0))
////            .state(State.PUBLISHED)
////            .build();
////
////    @Test
////    void patchAdminEvent() {
////        when(service.patchAdminEvent(1L, EventMapper.toAdminEventFromAdminDto(adminEventReceivedDto))).thenReturn(eventFull);
////
////        EventFullDto eventFullDto = controller.patchAdminEvent(1L, adminEventReceivedDto);
////
////        assertEquals(eventFullDto, EventMapper.toEventFullDto(eventFull));
////    }
////
////    @Test
////    void getAdminEvents() {
////        when(service.getAdminEvents(null, null, null, null, null, 0, 10)).thenReturn(List.of(eventFull));
////
////        List<EventFullDto> adminEvents = controller.getAdminEvents(null, null, null, null, null, 0, 10);
////
////        assertEquals(adminEvents, List.of(EventMapper.toEventFullDto(eventFull)));
////    }
//}
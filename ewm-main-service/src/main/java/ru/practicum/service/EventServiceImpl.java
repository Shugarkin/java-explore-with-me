package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.CategoriesMainServiceRepository;
import ru.practicum.dao.EventMainServiceRepository;
import ru.practicum.dao.UserMainServiceRepository;
import ru.practicum.dto.State;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.Categories;
import ru.practicum.model.Event;
import ru.practicum.model.EventFull;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {


    private final EventMainServiceRepository repository;

    private final CategoriesMainServiceRepository categoriesMainServiceRepository;

    private final UserMainServiceRepository userMainServiceRepository;

    @Transactional
    @Override
    public Event createEvent(long userId, Event event) {
        event.setCategory(categoriesMainServiceRepository.findById(event.getCategory().getId()).orElseThrow(() -> new NotFoundException("Категория не найдена")));
        event.setCreatedOn(LocalDateTime.now().plusNanos(0));
        event.setInitiator(userMainServiceRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден")));
        event.setState(State.PUBLISH_EVENT);
        return repository.save(event);
    }
}

//{
//        "annotation": "Сплав на байдарках похож на полет.",
//        "category": 2,
//        "description": "Сплав на байдарках похож на полет. На спокойной воде — это парение. На бурной, порожистой — выполнение фигур высшего пилотажа. И то, и другое дарят чувство обновления, феерические эмоции, яркие впечатления.",
//        "eventDate": "2024-12-31 15:10:05",
//        "location": {
//        "lat": 55.754167,
//        "lon": 37.62
//        },
//        "paid": true,
//        "participantLimit": 10,
//        "requestModeration": false,
//        "title": "Сплав на байдарках"
//        }
//
//{
//        "annotation": "Эксклюзивность нашего шоу гарантирует привлечение максимальной зрительской аудитории",
//        "category": {
//        "id": 1,
//        "name": "Концерты"
//        },
//        "confirmedRequests": 5,
//        "createdOn": "2022-09-06 11:00:23",
//        "description": "Что получится, если соединить кукурузу и полёт? Создатели \"Шоу летающей кукурузы\" испытали эту идею на практике и воплотили в жизнь инновационный проект, предлагающий свежий взгляд на развлечения...",
//        "eventDate": "2024-12-31 15:10:05",
//        "id": 1,
//        "initiator": {
//        "id": 3,
//        "name": "Фёдоров Матвей"
//        },
//        "location": {
//        "lat": 55.754167,
//        "lon": 37.62
//        },
//        "paid": true,
//        "participantLimit": 10,
//        "publishedOn": "2022-09-06 15:10:05",
//        "requestModeration": true,
//        "state": "PUBLISHED",
//        "title": "Знаменитое шоу 'Летающая кукуруза'",
//        "views": 999
//        }
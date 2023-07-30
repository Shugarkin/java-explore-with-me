package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.StatEventServiceRepository;
import ru.practicum.dao.StatServiceRepository;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.StatEventDto;
import ru.practicum.model.Stat;
import ru.practicum.model.StatEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatServiceImpl  implements StatService {

    private final StatEventServiceRepository serviceEventRepository;

    private final StatServiceRepository serviceRepository;

    @Transactional
    @Override
    public StatEvent postStatEvent(StatEvent event) {
        event.setTimestamp(LocalDateTime.now().toString());
        StatEvent newStatEvent = serviceEventRepository.save(event);
        postStat(newStatEvent);
        return newStatEvent;
    }

    @Transactional
    public void postStat(StatEvent event) {
        boolean answer = serviceRepository.existsByStatEventIdUri(event.getUri());
        if (!answer) {
            serviceRepository.save(Stat.builder().statEventId(event).hits(1).build());
        } else {
            Stat stat = serviceRepository.findByStatEventIdUri(event.getUri());
            int hits = stat.getHits();
            stat.setHits(hits + 1);
        }
    }

    @Override
    public List<Stat> getStatEvent(String ip, String start, String end, String[] uris, boolean unique) {
//        LocalDateTime newStart = LocalDateTime.parse(start);
//        LocalDateTime newEnd = LocalDateTime.parse(end);
//        List<Stat> list;
//        if (unique == true) {
//            if (uris == null) {
//                list = serviceEventRepository.findStat(newStart, newEnd, newStart, newEnd);
//            } else {
//                list = serviceEventRepository.findStatWithUris(newStart, newEnd, uris);
//            }
//        } else {
//            if (uris == null) {
//                list = serviceEventRepository.findStatUnique(newStart, newEnd, newStart, newEnd);
//            } else {
//                list = serviceEventRepository.findStatWithUrisUnique(newStart, newEnd, newStart, newEnd, uris);
//            }
//        }
//        if (list.isEmpty()) {
//            return List.of();
//        }
//
//
//        return list;

        List<Stat> list = new ArrayList<>();
        if (unique) {
            if (uris == null) {
                list = serviceRepository.findAllUniqueWithoutUris(start, end);
            }
        }
        return list;
    }
}

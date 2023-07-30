package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.StatEventServiceRepository;
import ru.practicum.dao.StatServiceRepository;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.StatEventDto;
import ru.practicum.mapper.StatMapper;
import ru.practicum.model.Stat;
import ru.practicum.model.StatEvent;
import ru.practicum.model.StatUniqueOrNot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        postStat(event);
        StatEvent newStatEvent = serviceEventRepository.save(event);

        return newStatEvent;
    }

    @Transactional
    public void postStat(StatEvent event) {
//        boolean answer = serviceRepository.existsByEventUri(event.getUri()); //проверяю есть ли данные в статистике уже
//
//        if (!answer) {
//            serviceRepository.save(Stat.builder().event(event).hits(1).hitsUnique(1).build()); //добавляю если нет
//        } else { //если данные есть
//            Stat stat = serviceRepository.findByEventUri(event.getUri()); //получаю стату по данному uri
//            //List<Stat> asd = serviceRepository.findAllByEventUri(event.getUri());
//            boolean answerUnique = serviceRepository.existsByEventUriAndEventIp(event.getUri(), event.getIp()); //проверяю есть ли стата с данным uri и ip
//            if (answerUnique) { //если ip полученого запроса не уникальный
//                long hits = stat.getHits();
//                stat.setHits(hits + 1);
//            } else { //если уникальный
//                long hits = stat.getHits();
//                stat.setHits(hits + 1);
//                long hitsUnique = stat.getHitsUnique();
//                stat.setHitsUnique(hitsUnique + 1);
//            }
//        }
        boolean answer = serviceRepository.existsByUri(event.getUri()); //проверяю есть ли данные в статистике уже
        if (!answer) {
            serviceRepository.save(Stat.builder().app(event.getApp()).uri(event.getUri()).hits(1).hitsUnique(1).build()); //добавляю если нет
        } else { //если данные есть
        ????
        }

        Stat stat = event.getStat();
        stat.setHitsUnique(1);
        stat.setHits(1);
        serviceRepository.save(stat);
    }

    @Override
    public List<StatUniqueOrNot> getStatEvent(String ip, String start, String end, List<String> uris, boolean unique) {
        List<StatUniqueOrNot> list= new ArrayList<>();
            if (uris.isEmpty()) { //если список uri пуст
                if (unique) { //если надо учитывать уникальность
                    list = StatMapper.toListUnique(serviceRepository.findAllByEventTimestampBetween(start, end));
                } else { //если не надо учитывать уникальность
                    list = StatMapper.toListNotUnique(serviceRepository.findAllByEventTimestampBetween(start, end));
                }
            } else { //если список uri не пуст
                if (unique) { //если надо учитывать уникальность
                    list = StatMapper.toListUnique(serviceRepository.findALlByEventTimestampBetweenAndWithUris(start, end, uris));
                } else { //если не надо учитывать уникальность
                    list = StatMapper.toListNotUnique(serviceRepository.findALlByEventTimestampBetweenAndWithUris(start, end, uris));
                }
            }

        return list;
    }
}

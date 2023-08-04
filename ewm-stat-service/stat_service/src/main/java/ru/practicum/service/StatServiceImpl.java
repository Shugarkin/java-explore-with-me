package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.StatServiceRepository;
import ru.practicum.mapper.StatMapper;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StatServiceImpl  implements StatService {

    private final StatServiceRepository serviceRepository;

    @Transactional
    @Override
    public Stat postStat(Stat stat) {
        boolean answer = serviceRepository.existsByUri(stat.getUri()); //проверяю есть ли уже записть с данным запросом uri
        Stat newStat;
        if (answer) { //если есть
            boolean answerIp = serviceRepository.existsByUriAndIp(stat.getUri(), stat.getIp()); //проверяю были ли обращения
                                                                                                //с этого ip
            if (answerIp) { //если были то обновляю только обший hit
                newStat = serviceRepository.findByUri(stat.getUri());
                long hit = newStat.getHits();
                newStat.setHits(hit + 1);
                log.info("update stat parameter hits");
            } else { //если нет то и уникальный
                newStat = serviceRepository.findByUri(stat.getUri());
                long hit = newStat.getHits();
                long hitUnique = newStat.getHitsUnique();
                newStat.setHits(hit + 1);
                log.info("update stat parameter hits");
                newStat.setHitsUnique(hitUnique + 1);
                log.info("update stat parameter hits unique");
            }
        } else { //если нет
            stat.setHits(1); //добавляю запись о общем посещении
            stat.setHitsUnique(1); //добавляю запись о уникальном посещении
            newStat = serviceRepository.save(stat);
            log.info("create new stat");
        }
        return newStat;
    }


    @Override
    public List<StatUniqueOrNot> getStat(String start, String end, List<String> uris, boolean unique) {
            List<StatUniqueOrNot> list;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateStart = LocalDateTime.parse(start, formatter);
        LocalDateTime dateEnd = LocalDateTime.parse(end, formatter);
            if (uris.isEmpty()) { //если список uri пуст
                if (unique) { //если надо учитывать уникальность
                    list = StatMapper.toListUnique(serviceRepository.findByTimestampBetween(dateStart, dateEnd));
                    log.info("get unique list without uris");
                } else { //если не надо учитывать уникальность
                    list = StatMapper.toListNotUnique(serviceRepository.findByTimestampBetween(dateStart, dateEnd));
                    log.info("get not unique list without uris");
                }
            } else { //если список uri не пуст
                if (unique) { //если надо учитывать уникальность
                    list = StatMapper.toListUnique(serviceRepository.findByTimestampBetweenAndUri(dateStart, dateEnd, uris));
                    log.info("get unique list with uris");
                } else { //если не надо учитывать уникальность
                    list = StatMapper.toListNotUnique(serviceRepository.findByTimestampBetweenAndUri(dateStart, dateEnd, uris));
                    log.info("get not unique list with uris");
                }
            }
        return list;
    }
}

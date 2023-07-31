package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.StatServiceRepository;
import ru.practicum.mapper.StatMapper;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatServiceImpl  implements StatService {

    private final StatServiceRepository serviceRepository;

    @Transactional
    @Override
    public Stat postStatEvent(Stat stat) {
        boolean answer = serviceRepository.existsByUri(stat.getUri()); //проверяю есть ли уже записть с данным запросом uri
        Stat newStat;
        if (answer) { //если есть
            boolean answerIp = serviceRepository.existsByUriAndIp(stat.getUri(), stat.getIp()); //проверяю были ли обращения
                                                                                                //с этого ip
            if (answerIp) { //если были то обновляю только обший hit
                newStat = serviceRepository.findByUri(stat.getUri());
                long hit = newStat.getHits();
                newStat.setHits(hit + 1);
            } else { //если нет то и уникальный
                newStat = serviceRepository.findByUri(stat.getUri());
                long hit = newStat.getHits();
                long hitUnique = newStat.getHitsUnique();
                newStat.setHits(hit + 1);
                newStat.setHitsUnique(hitUnique + 1);
            }
        } else { //если нет
            stat.setHits(1); //добавляю запись о общем посещении
            stat.setHitsUnique(1); //добавляю запись о уникальном посещении
            newStat = serviceRepository.save(stat);
        }


        return newStat;
    }


    @Override
    public List<StatUniqueOrNot> getStatEvent(String ip, String start, String end, List<String> uris, boolean unique) {

            List<StatUniqueOrNot> list;
            if (uris.isEmpty()) { //если список uri пуст
                if (unique) { //если надо учитывать уникальность
                    list = StatMapper.toListUnique(serviceRepository.findByTimestampBetween(start, end));
                } else { //если не надо учитывать уникальность
                    list = StatMapper.toListNotUnique(serviceRepository.findByTimestampBetween(start, end));
                }
            } else { //если список uri не пуст
                if (unique) { //если надо учитывать уникальность
                    list = StatMapper.toListUnique(serviceRepository.findByTimestampBetweenAndUri(start, end, uris));
                } else { //если не надо учитывать уникальность
                    list = StatMapper.toListNotUnique(serviceRepository.findByTimestampBetweenAndUri(start, end, uris));
                }
            }
        return list;
    }
}

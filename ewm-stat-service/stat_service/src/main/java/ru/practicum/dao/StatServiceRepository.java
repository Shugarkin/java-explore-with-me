package ru.practicum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;

import java.time.LocalDateTime;
import java.util.List;

public interface StatServiceRepository extends JpaRepository<Stat, Long> {

    @Query("select new ru.practicum.model.StatUniqueOrNot(stat.ip, stat.uri, count(stat.ip)) " +
            "from Stat as stat " +
            "where stat.timestamp between ?1 and ?2 " +
            "group by stat.ip, stat.uri " +
            "order by count(stat.ip) desc ")
    List<StatUniqueOrNot> findAllByTimestampBetween(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.model.StatUniqueOrNot(stat.ip, stat.uri, count(stat.ip)) " +
            "from Stat as stat " +
            "where stat.timestamp between ?1 and ?2 " +
            "group by stat.ip, stat.uri " +
            "order by count(stat.ip) desc ")
    List<StatUniqueOrNot> findAllByUriAndIp(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.model.StatUniqueOrNot(stat.ip, stat.uri, count(distinct stat.ip)) " +
            "from Stat as stat " +
            "where stat.timestamp between ?1 and ?2 and stat.uri in ?3 " +
            "group by stat.ip, stat.uri " +
            "order by count(distinct stat.ip) desc ")
    List<StatUniqueOrNot> findAllByUriAndIpAndUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.model.StatUniqueOrNot(stat.ip, stat.uri, count(stat.ip)) " +
            "from Stat as stat " +
            "where stat.timestamp between ?1 and ?2 and stat.uri in ?3 " +
            "group by stat.ip, stat.uri " +
            "order by count(stat.ip) desc ")
    List<StatUniqueOrNot> findAllByUriAndIpAndUrisNotUnique(LocalDateTime start, LocalDateTime end, List<String> uris);
}

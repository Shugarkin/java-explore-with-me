package ru.practicum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

public interface StatServiceRepository extends JpaRepository<Stat, Long> {

    boolean existsByUri(String uri);

    Stat findByUri(String uri);

    boolean existsByUriAndIp(String uri, String ip);

    @Query("select stat " +
            "from Stat as stat " +
            "where stat.timestamp  " +
            "between ?1 and " +
            "?2 " +
            "and stat.uri in ?3 " +
            "order by  stat.hits desc ")
    List<Stat> findByTimestampBetweenAndUri(LocalDateTime start, LocalDateTime end, List<String> uris);

    List<Stat> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

}

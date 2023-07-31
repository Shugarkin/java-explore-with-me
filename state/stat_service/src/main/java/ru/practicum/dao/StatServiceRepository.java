package ru.practicum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Stat;

import java.util.List;

public interface StatServiceRepository extends JpaRepository<Stat, Long> {

    boolean existsByUri(String uri);

    Stat findByUri(String uri);

    boolean existsByUriAndIp(String uri, String ip);


    @Query("select stat " +
            "from Stat as stat " +
            "where parsedatetime(stat.timestamp,  'yyyy-MM-dd HH:mm:ss') " +
            "between parsedatetime(?1,  'yyyy-MM-dd HH:mm:ss') and parsedatetime(?2,  'yyyy-MM-dd HH:mm:ss') " +
            "and stat.uri in ?3 " +
            "order by stat.hits desc ")
    List<Stat> findByTimestampBetweenAndUri(String start, String end, List<String> uris);

    List<Stat> findByTimestampBetween(String start, String end);

}

package ru.practicum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Stat;

import java.util.List;

public interface StatServiceRepository extends JpaRepository<Stat, Long> {


//    @Query("select stat " +
//            "from Stat  as stat " +
//            "where parsedatetime(stat.event.timestamp,  'yyyy-MM-dd HH:mm:ss') between parsedatetime(?1,  'yyyy-MM-dd HH:mm:ss') and parsedatetime(?2,  'yyyy-MM-dd HH:mm:ss') " +
//            "order by stat.statId")
//    @Query(value = "select s.id, s.app, s.uri, s.hits, s.hits_unique " +
//            "from stat_event as se join stat as s on se.stat_id = s.id " +
//            "where parsedatetime(se.time_stamp,  'yyyy-MM-dd HH:mm:ss') between parsedatetime(?1,  'yyyy-MM-dd HH:mm:ss') and parsedatetime(?2,  'yyyy-MM-dd HH:mm:ss') " +
//            "order by s.Id", nativeQuery = true)
@Query("select new ru.practicum.model.Stat(se.stat.statId, se.stat.app, se.stat.uri, se.stat.hits, se.stat.hitsUnique) " +
        "from StatEvent as se " +
        "where parsedatetime(se.timestamp,  'yyyy-MM-dd HH:mm:ss') between parsedatetime(?1,  'yyyy-MM-dd HH:mm:ss') and parsedatetime(?2,  'yyyy-MM-dd HH:mm:ss') " +
        "order by se.stat.statId")
    List<Stat> findAllByEventTimestampBetween(String start, String end);


    @Query("select new ru.practicum.model.Stat(se.stat.statId, se.stat.app, se.stat.uri, se.stat.hits, se.stat.hitsUnique) " +
            "from StatEvent as se " +
            "where parsedatetime(se.timestamp,  'yyyy-MM-dd HH:mm:ss') between parsedatetime(?1,  'yyyy-MM-dd HH:mm:ss') and parsedatetime(?2,  'yyyy-MM-dd HH:mm:ss') " +
            "and se.uri in ?3 " +
            "order by se.stat.statId")
//@Query(value = "select s.id, s.app, s.uri, s.hits, s.hits_unique " +
//        "from stat_event as se join stat as s on se.stat_id = s.id " +
//        "where parsedatetime(se.time_stamp,  'yyyy-MM-dd HH:mm:ss') between parsedatetime(?1,  'yyyy-MM-dd HH:mm:ss') and parsedatetime(?2,  'yyyy-MM-dd HH:mm:ss') " +
//        "and se.uri in ?3 " +
//        "order by s.Id ", nativeQuery = true)
    List<Stat> findALlByEventTimestampBetweenAndWithUris(String start, String end, List<String> uris);

    boolean existsByUri(String uri);
}

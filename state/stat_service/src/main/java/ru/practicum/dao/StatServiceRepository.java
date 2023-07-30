package ru.practicum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Stat;

import java.util.List;

public interface StatServiceRepository extends JpaRepository<Stat, Long> {

    //Stat findByStatEventIdUri(String uri);

    @Query(value = "select v.view_id, e, v.hits " +
            "from view_stats as v join endpoint_hit as e on v.endpoint_hit_id = e.id " +
            "where (cast(e.time_stamp as time_stamp)) between ?1 and ?2 " +
            "group by v.view_id ", nativeQuery = true)
    List<Stat> findAllUniqueWithoutUris(String start, String end);




    boolean existsByEventUri(String uri);

    @Query("select stat " +
            "from Stat  as stat " +
            "where parsedatetime(stat.event.timestamp,  'yyyy-MM-dd HH:mm:ss') between parsedatetime(?1,  'yyyy-MM-dd HH:mm:ss') and parsedatetime(?2,  'yyyy-MM-dd HH:mm:ss') " +
            "order by stat.statId")
    List<Stat> findAllByEventTimestampBetween(String start, String end);


    @Query("select stat " +
            "from Stat  as stat " +
            "where parsedatetime(stat.event.timestamp,  'yyyy-MM-dd HH:mm:ss') between parsedatetime(?1,  'yyyy-MM-dd HH:mm:ss') and parsedatetime(?2,  'yyyy-MM-dd HH:mm:ss') " +
            "and stat.event.uri in ?3 " +
            "order by stat.statId")
    List<Stat> findALlByEventTimestampBetweenAndWithUris(String start, String end, List<String> uris);

    Stat findByEventUriAndEventIp(String uri, String ip);

    Stat findByEventUri(String uri);

    boolean existsByEventUriAndEventIp(String uri, String ip);

    List<Stat> findAllByEventUri(String uri);


//    @Query("select stat " +
//            "from Stat  as stat " +
//            "where parsedatetime(stat.event.timestamp,  'yyyy-MM-dd HH:mm:ss') between parsedatetime(?1,  'yyyy-MM-dd HH:mm:ss') and parsedatetime(?2,  'yyyy-MM-dd HH:mm:ss') " +
//            "and Distinct(stat.event.uri) " +
//            "order by stat.statId")
//    List<Stat> findAllByEventTimestampBetweenWithUnique(String start, String end);
}

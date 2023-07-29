package ru.practicum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.Stat;
import ru.practicum.model.StatEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StatEventServiceRepository extends JpaRepository<StatEvent, Long> {

    @Query("select new Stat(stat.app, stat.uri, " +
            "(select count(*) from stat where stat.uri like " +
            "(select stat.uri from stat where stat.timestamp > ?1 and stat.timestamp < ?2 ))) " +
            "from StatEvent as stat " +
            "where stat.timestamp > ?3 and stat.timestamp < ?4 " +
            "order by stat.uri ")
    List<Stat> findStat(LocalDateTime start, LocalDateTime end, LocalDateTime start1, LocalDateTime end1);

    @Query("select new Stat(stat.app, stat.uri, " +
            "(select count(*) from stat where stat.uri like " +
            "(select stat.uri from stat where stat.timestamp > :start and stat.timestamp < :end ))) " +
            "from StatEvent as stat " +
            "where stat.timestamp > :start and stat.timestamp < :end and stat.uri in :uris " +
            "order by stat.uri ")
    List<Stat> findStatWithUris(@Param("start") LocalDateTime start, @Param("end")LocalDateTime end, @Param("uris") String[] uris);

    @Query("select new Stat(stat.app, stat.uri, " +
            "(select count(*) from stat where stat.uri like " +
            "(select stat.uri from stat where stat.timestamp > ?1 and stat.timestamp < ?2 ))) " +
            "from StatEvent as stat " +
            "where stat.timestamp > ?3 and stat.timestamp < ?4 and DISTINCT(stat.uri) " +
            "order by stat.uri ")
    List<Stat> findStatUnique(LocalDateTime start, LocalDateTime end, LocalDateTime start1, LocalDateTime end1);


    @Query("select new Stat(stat.app, stat.uri, " +
            "(select count(*) from stat where stat.uri like  " +
            "(select stat.uri from stat where stat.timestamp > ?1 and stat.timestamp < ?2 ))) " +
            "from StatEvent as stat " +
            "where stat.timestamp > ?3 and stat.timestamp < ?4 and stat.uri in :uris and DISTINCT(stat.uri)" +
            "order by stat.uri ")
    List<Stat> findStatWithUrisUnique(LocalDateTime newStart, LocalDateTime newEnd, LocalDateTime newStart1, LocalDateTime newEnd1, @Param("uris") String[] uris);
}

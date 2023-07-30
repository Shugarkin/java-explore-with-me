package ru.practicum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Stat;

import java.util.List;

public interface StatServiceRepository extends JpaRepository<Stat, Long> {

    Stat findByStatEventIdUri(String uri);

    @Query(value = "select v.view_id, e, v.hits " +
            "from view_stats as v join endpoint_hit as e on v.endpoint_hit_id = e.id " +
            "where (cast(e.time_stamp as time_stamp)) between ?1 and ?2 " +
            "group by v.view_id ", nativeQuery = true)
    List<Stat> findAllUniqueWithoutUris(String start, String end);

    boolean existsByStatEventIdUri(String uri);
}

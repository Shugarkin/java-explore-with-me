package ru.practicum.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.model.ConfirmedRequestShort;
import ru.practicum.main.model.Request;

import java.util.List;

public interface RequestMainServiceRepository extends JpaRepository<Request, Long> {

    @Query("select new ru.practicum.main.model.ConfirmedRequestShort(req.event.id ,count(req.id)) " +
            "from Request as req " +
            "where req.event.id in ?1 " +
            "and req.status = 'CONFIRMED' " +
            "group by req.event.id ")
    List<ConfirmedRequestShort> countByEventId(List<Long> longs);
}

package ru.practicum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Request;

import java.util.List;
import java.util.Set;

public interface RequestMainServiceRepository extends JpaRepository<Request, Long> {

    @Query("select count(req.id) " +
            "from Request as req " +
            "where req.event.id in ?1 ")
    long countByEventId(List<Long> longs);
}

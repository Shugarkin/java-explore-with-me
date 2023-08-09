package ru.practicum.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.User;

import java.util.List;

public interface UserMainServiceRepository extends JpaRepository<User, Long> {

    @Query("select us " +
            "from User as us " +
            "where us.id in ?1 ")
    List<User> findAllByIds(List<Long> ids, Pageable pageable);

    @Query("select us " +
            "from User as us")
    List<User> findAllUser(Pageable pageable);
}

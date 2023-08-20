package ru.practicum.main.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.model.Compilations;

import java.util.List;

public interface CompilationMainServiceRepository extends JpaRepository<Compilations, Long> {


    @Query("select com " +
            "from Compilations as com " +
            "where com.pinned = ?1 or (com.pinned = false or com.pinned = true) ")
    List<Compilations> findAllByPinned(Boolean pinned, Pageable pageable);
}

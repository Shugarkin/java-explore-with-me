package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.dao.CompilationMainServiceRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.mapper.CompilationMapper;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.model.CompilationShort;
import ru.practicum.main.model.Compilations;
import ru.practicum.main.model.EventShort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PublicCompilationServiceImpl implements PublicCompilationService {

    private final CompilationMainServiceRepository repository;

    private final StatService statService;

    @Override
    public CompilationShort getCompilationById(long compId) {
        Compilations compilations = repository.findById(compId).orElseThrow(() -> new NotFoundException("Данной подборки не существует"));
        log.info("get compilation by id");
        return toCompilationShort(compilations);
    }

    @Override
    public List<Compilations> getCompilation(Boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size, Sort.by("id").ascending());

        List<Compilations> list = repository.findAllByPinned(pinned, pageable);

        log.info("get compilation");
        return list;
    }

    private CompilationShort toCompilationShort(Compilations compilations) {
        Map<Long, Long> view = statService.toView(compilations.getEvents());

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(compilations.getEvents());

        List<EventShort> listEventShort = compilations.getEvents().stream().map(event ->
                        EventMapper.toEventShort(event, view.getOrDefault(event.getId(), 0L), confirmedRequest.getOrDefault(event.getId(), 0L)))
                .collect(Collectors.toList());

        return  CompilationMapper.toCompilationShort(compilations, listEventShort);
    }
}

package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.dao.CompilationMainServiceRepository;
import ru.practicum.main.dao.EventMainServiceRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.mapper.CompilationMapper;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationsServiceImpl implements AdminCompilationsService {

    private final CompilationMainServiceRepository repository;

    private final EventMainServiceRepository eventMainServiceRepository;

    private final StatService statService;

    @Override
    public CompilationShort createCompilation(NewCompilation newCompilation) {

        List<Event> events = eventMainServiceRepository.findAllById(newCompilation.getEvents());

        if (newCompilation.getPinned() == null) {
            newCompilation.setPinned(false);
        }

        Compilations compilations = repository.save(CompilationMapper.toCompilation(newCompilation, events));

        Map<Long, Long> view = statService.toView(events);

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(events);

        List<EventShort> listEventShort = compilations.getEvents().stream().map(event ->
                EventMapper.toEventShort(event, view.getOrDefault(event.getId(), 0L), confirmedRequest.getOrDefault(event.getId(), 0L)))
                .collect(Collectors.toList());

        log.info("create new compilation");
        return CompilationMapper.toCompilationShort(compilations, listEventShort);
    }

    @Override
    public void deleteCompilation(long compId) {
        boolean answer = repository.existsById(compId);
        if (!answer) {
            throw new NotFoundException("Данной подборки не существует");
        }
        log.info("delete compilation");
        repository.deleteById(compId);
    }

    @Override
    public CompilationShort patchCompilation(long compId, NewCompilation newCompilation) {
        Compilations compilations = repository.findById(compId).orElseThrow(() -> new NotFoundException("Данной подборки не существует"));

        if (newCompilation.getPinned() != null) {
            compilations.setPinned(newCompilation.getPinned());
        }

        if (newCompilation.getTitle() != null && !newCompilation.getTitle().isBlank()) {
            compilations.setTitle(newCompilation.getTitle());
        }

        if (newCompilation.getEvents() != null) {
            compilations.setEvents(new HashSet<>(eventMainServiceRepository.findAllById(newCompilation.getEvents())));
        }

        Map<Long, Long> view = statService.toView(compilations.getEvents());

        Map<Long, Long> confirmedRequest = statService.toConfirmedRequest(compilations.getEvents());

        List<EventShort> listEventShort = compilations.getEvents().stream().map(event ->
                        EventMapper.toEventShort(event, view.getOrDefault(event.getId(), 0L), confirmedRequest.getOrDefault(event.getId(), 0L)))
                .collect(Collectors.toList());

        log.info("patch compilation");
        return CompilationMapper.toCompilationShort(compilations, listEventShort);

    }

}

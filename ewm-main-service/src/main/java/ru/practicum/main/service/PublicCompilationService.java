package ru.practicum.main.service;

import ru.practicum.main.model.CompilationShort;

import java.util.List;

public interface PublicCompilationService {
    CompilationShort getCompilationById(long compId);

    List<CompilationShort> getCompilation(Boolean pinned, int from, int size);
}

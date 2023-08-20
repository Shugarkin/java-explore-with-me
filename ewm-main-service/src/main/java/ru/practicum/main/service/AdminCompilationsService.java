package ru.practicum.main.service;

import ru.practicum.main.model.CompilationShort;
import ru.practicum.main.model.NewCompilation;

public interface AdminCompilationsService {
    CompilationShort createCompilation(NewCompilation newCompilation);

    void deleteCompilation(long compId);

    CompilationShort patchCompilation(long compId, NewCompilation newCompilation);
}

package ru.practicum.main.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.dto.CompilationsDto;
import ru.practicum.main.dto.NewCompilationDto;
import ru.practicum.main.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {

    public CompilationsDto toCompilationsDto(CompilationShort compilations) {
        return CompilationsDto.builder()
                .events(EventMapper.toListEventShortDto(compilations.getEvents()))
                .id(compilations.getId())
                .pinned(compilations.getPinned())
                .title(compilations.getTitle())
                .build();
    }


    public NewCompilation toNewCompilation(NewCompilationDto newCompilationDto) {
        return NewCompilation.builder()
                .events(newCompilationDto.getEvents())
                .pinned(newCompilationDto.getPinned())
                .title(newCompilationDto.getTitle())
                .build();
    }

    public Compilations toCompilation(NewCompilation newCompilation, List<Event> list) {
        return Compilations.builder()
                .title(newCompilation.getTitle())
                .pinned(newCompilation.getPinned())
                .events(new HashSet<>(list))
                .build();
    }

    public CompilationShort toCompilationShort(Compilations compilations, List<EventShort> list) {
        return CompilationShort.builder()
                .title(compilations.getTitle())
                .pinned(compilations.getPinned())
                .events(list)
                .id(compilations.getId())
                .build();
    }

    public List<CompilationsDto> toListCompilationDto(List<CompilationShort> list) {
        return list.stream().map(CompilationMapper::toCompilationsDto).collect(Collectors.toList());
    }
}

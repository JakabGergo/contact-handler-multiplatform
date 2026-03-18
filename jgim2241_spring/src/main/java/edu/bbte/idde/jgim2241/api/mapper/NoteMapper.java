package edu.bbte.idde.jgim2241.api.mapper;

import edu.bbte.idde.jgim2241.api.dto.in.NoteCreationDto;
import edu.bbte.idde.jgim2241.api.dto.outgoing.NoteResponseDto;
import edu.bbte.idde.jgim2241.model.Note;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class NoteMapper {
    @IterableMapping(elementTargetType = NoteResponseDto.class)
    public abstract Collection<NoteResponseDto> modelsToDtos(Iterable<Note> model);

    public abstract NoteResponseDto modelToDto(Note model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contact", ignore = true)
    public abstract Note creationDtoToModel(NoteCreationDto creationDto);
}

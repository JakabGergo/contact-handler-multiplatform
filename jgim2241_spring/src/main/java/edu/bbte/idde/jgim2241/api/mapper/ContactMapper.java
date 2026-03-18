package edu.bbte.idde.jgim2241.api.mapper;

import edu.bbte.idde.jgim2241.api.dto.in.ContactCreationDto;
import edu.bbte.idde.jgim2241.api.dto.outgoing.ContactDetailsDto;
import edu.bbte.idde.jgim2241.api.dto.outgoing.ContactReduceDto;
import edu.bbte.idde.jgim2241.model.Contact;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class ContactMapper {
    @IterableMapping(elementTargetType = ContactReduceDto.class)
    public abstract Collection<ContactReduceDto> modelsToReducedDtos(Iterable<Contact> model);

    public abstract ContactDetailsDto modelToDetailsDto(Contact model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notes", ignore = true)
    public abstract Contact creationDtoToModel(ContactCreationDto creationDto);
}

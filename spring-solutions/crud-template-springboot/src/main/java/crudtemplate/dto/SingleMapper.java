package crudtemplate.dto;

import java.util.List;

import org.mapstruct.Mapper;

import crudtemplate.model.Single;

@Mapper(componentModel = "spring")
public interface SingleMapper {

    SingleDto toDto(Single entity);
    List<SingleDto> toDto(List<Single> entities);

    Single fromDto(SingleDto entityDto);
    List<Single> fromDto(List<SingleDto> entityDtos);
    
    Single fromCreateCommand(CreateSingleCommand entityCreateCommand);

}

package crudtemplate.dto;

import java.util.List;

import org.mapstruct.Mapper;

import crudtemplate.model.Multiple;

@Mapper(componentModel = "spring")
public interface MultipleMapper {

    MultipleDto toDto(Multiple entity);
    List<MultipleDto> toDto(List<Multiple> entities);

    Multiple fromDto(MultipleDto entityDto);
    List<Multiple> fromDto(List<MultipleDto> entityDtos);
    
    Multiple fromCreateCommand(CreateMultipleCommand entityCreateCommand);

}

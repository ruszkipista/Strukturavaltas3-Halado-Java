package training360.sv2023jvjbfkepesitovizsga.dtos;

import java.util.List;

import org.mapstruct.Mapper;

import training360.sv2023jvjbfkepesitovizsga.model.School;

@Mapper(componentModel = "spring")
public interface SchoolMapper {

    SchoolDto toDto(School entity);
    List<SchoolDto> toDto(List<School> entities);

    School fromDto(SchoolDto entityDto);
    List<School> fromDto(List<SchoolDto> entityDtos);
}

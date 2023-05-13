package flights.dtos;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import flights.model.Airplane;

@Mapper(componentModel = "spring")
public interface AirplaneMapper {

    AirplaneDto toDto(Airplane entity);
    List<AirplaneDto> toDto(List<Airplane> entities);

    Airplane fromDto(AirplaneDto entityDto);
    List<Airplane> fromDto(List<AirplaneDto> entityDtos);
    
    Airplane fromCreateCommand(CreateAirplaneCommand entityCreateCommand);
    Airplane fromUpdateCommand(UpdateAirplaneCommand entityUpdateCommand, @MappingTarget Airplane entity);

}

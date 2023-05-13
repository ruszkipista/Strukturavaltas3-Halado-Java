package locations.model;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationDto toDto(Location location);
    List<LocationDto> toDto(List<Location> locations);

    Location fromDto(LocationDto locationDto);
    List<Location> fromDto(List<LocationDto> locationDtos);
    
    LocationCreateCommand toCreateCommand(Location location);
    Location fromCreateCommand(LocationCreateCommand locationCreateCommand);

    LocationCreateCommand toUpdateCommand(Location location);
    Location fromUpdateCommand(LocationUpdateCommand locationUpdateCommand, @MappingTarget Location location);

}

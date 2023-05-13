package flights.dtos;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import flights.model.Route;

@Mapper(componentModel = "spring")
public interface RouteMapper {

    RouteDto toDto(Route entity);
    List<RouteDto> toDto(List<Route> entities);

    Route fromDto(RouteDto entityDto);
    List<Route> fromDto(List<RouteDto> entityDtos);
    
    Route fromCreateCommand(CreateRouteCommand entityCreateCommand);
    Route fromUpdateCommand(UpdateRouteCommand entityUpdateCommand, @MappingTarget Route entity);

}

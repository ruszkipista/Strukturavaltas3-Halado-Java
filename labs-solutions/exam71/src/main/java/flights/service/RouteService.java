package flights.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import flights.dtos.AirplaneDto;
import flights.dtos.AirplaneMapper;
import flights.dtos.CreateRouteCommand;
import flights.dtos.RouteDto;
import flights.dtos.RouteMapper;
import flights.dtos.UpdateRouteCommand;
import flights.exception.AirplaneAlreadyScheduledForDayException;
import flights.exception.AirplaneNotFoundException;
import flights.exception.RouteNotFoundException;
import flights.model.Airplane;
import flights.model.Route;
import flights.repository.AirplaneRepository;
import flights.repository.RouteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RouteService {
    private RouteRepository routeRepo;
    private RouteMapper routeMapper;
    private AirplaneRepository airplaneRepo;
    private AirplaneMapper airplaneMapper;

    public List<RouteDto> getRoutes() {
        return this.routeMapper.toDto(routeRepo.findAll());
    }

    public RouteDto getRouteById(long id) {
        return this.routeMapper.toDto(routeRepo.findById(id)
                .orElseThrow(() -> new RouteNotFoundException(id)));
    }

    @Transactional
    public RouteDto createRoute(long airplaneId, CreateRouteCommand command) {
        Airplane airplane = airplaneRepo.findById(airplaneId)
                .orElseThrow(() -> new AirplaneNotFoundException(airplaneId));
        Route routeToCreate = this.routeMapper.fromCreateCommand(command);
        routeToCreate.setAirplane(airplane);
        Route route;
        try {
            route = routeRepo.save(routeToCreate);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getCause().getCause());
            throw new AirplaneAlreadyScheduledForDayException(airplaneId, command.getDateOfFlight(), e);
        }
        return this.routeMapper.toDto(route);
    }

    @Transactional
    public RouteDto updateRouteById(long routeId, UpdateRouteCommand updateCommand) {
        Route entity;
        try {
            entity = routeRepo.findById(routeId)
                    .orElseThrow(() -> new IllegalArgumentException("Route not found: " + routeId));
        } catch (Exception e) {
            throw new RouteNotFoundException(routeId, e);
        }
        Route entityUpdated = this.routeMapper.fromUpdateCommand(updateCommand, entity);
        entityUpdated = routeRepo.save(entityUpdated);
        return this.routeMapper.toDto(entityUpdated);
    }

    @Transactional
    public void removeRouteById(long id) {
        routeRepo.findById(id).orElseThrow(() -> new RouteNotFoundException(id));
        routeRepo.deleteById(id);
    }

    @Transactional
    public AirplaneDto cancelAirplaneRouteById(long airplaneId, long routeId) {
        Airplane airplane = airplaneRepo.findById(airplaneId)
                            .orElseThrow(() -> new AirplaneNotFoundException(airplaneId));
        Optional<Route> route = airplane.getRoutes().stream()
                                        .filter(r->r.getId()==routeId)
                                        .findAny();
        airplane.removeRoute(route.orElseThrow(()->new RouteNotFoundException(routeId)));
        return this.airplaneMapper.toDto(airplane);
    }

}

package movies.model;

import java.util.List;
import java.util.Optional;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDto toDto(Movie movie);
    List<MovieDto> toDto(List<Movie> movies);

    Movie fromDto(MovieDto movieDto);
    List<Movie> fromDto(List<MovieDto> movieDtos);
    
    MovieCreateCommand toCreateCommand(Movie movie);
    Movie fromCreateCommand(MovieCreateCommand createCommand);

    MovieCreateCommand toUpdateCommand(Movie movie);
    Movie fromUpdateCommand(MovieUpdateCommand updateCommand, @MappingTarget Movie movie);

    default Integer mapOptInt(Optional<Integer> value){
        return value.get();
    }

    default String mapOptStr(Optional<String> value){
        return value.get(); 
    }

    @Condition
    default boolean isPresent(Optional value) {
        return value != null && value.isPresent();
    }

}

package movies.model;

import java.util.List;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    RatingDto toDto(Rating rating);
    List<RatingDto> toDto(List<Rating> ratings);

    Rating fromDto(RatingDto ratingDto);
    List<Rating> fromDto(List<RatingDto> ratingDtos);

    @Condition
    default boolean isNotNull(Object value) {
        return value != null;
    }

}
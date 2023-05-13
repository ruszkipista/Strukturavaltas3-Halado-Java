package movies.model;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieUpdateCommand {
    private Optional<String> title;
    private Optional<Integer> lengthInMins;
}
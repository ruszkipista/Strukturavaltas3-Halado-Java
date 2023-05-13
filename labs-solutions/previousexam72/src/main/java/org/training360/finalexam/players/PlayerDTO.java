package org.training360.finalexam.players;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.training360.finalexam.teams.Team;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {

    private Long id;

    private String name;

    private LocalDate dateOfBirth;

    private PositionType position;
}

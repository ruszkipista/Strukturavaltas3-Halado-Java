package sportresults.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sportresults.model.Sex;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateAthleteCommand {

    @NotBlank(message = "Name cannot be blank!")
    @NotEmpty(message = "Name cannot be blank!")
    private String name;

    private Sex sex;

}

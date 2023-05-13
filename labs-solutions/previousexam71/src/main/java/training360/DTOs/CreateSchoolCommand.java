package training360.DTOs;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CreateSchoolCommand {

    @NotBlank(message="Schoolname cannot be blank!")
    private String name;

    private String postalCode;
    private String city;
    private String street;
    private int houseNumber;

}

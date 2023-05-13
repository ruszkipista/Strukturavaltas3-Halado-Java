package sportresults.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    private Long id;
    private String place;
    private LocalDate resultDate;
    private String sportType;
    private double measure;
    private char measureUnit;
}

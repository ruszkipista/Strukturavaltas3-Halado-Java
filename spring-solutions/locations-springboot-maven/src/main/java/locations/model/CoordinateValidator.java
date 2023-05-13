package locations.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CoordinateValidator implements ConstraintValidator<Coordinate, Double> {

    private CoordinateType type;

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext constraintValidatorContext) {
        if (this.type == CoordinateType.LAT) {
            return -90.0 <= value && value <= 90.0;
        } else {
            return -180.0 <= value && value <= 180.0;
        }
    }

    @Override
    public void initialize(Coordinate constraintAnnotation) {
        this.type = constraintAnnotation.type();
    }
}
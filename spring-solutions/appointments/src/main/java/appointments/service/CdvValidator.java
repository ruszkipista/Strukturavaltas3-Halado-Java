package appointments.service;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CdvValidator implements ConstraintValidator<ValidCDV, Long> {

    @Override
    public boolean isValid(Long number, ConstraintValidatorContext context) {
        return checkCdv(number);
    }

    public boolean checkCdv(Long number) {
        if (number < 1_000_000_000L || 9_999_999_999L < number ){
            return false;
        }
        long lastDigit = number % 10L;
        number = removeLastDigit(number, lastDigit);
        long checkDigit = 0L;
        for (long i=9L; i>0L; i--){ 
            long digit = number % 10L;
            checkDigit += digit * i;
            number = removeLastDigit(number, digit);
        }
        checkDigit = checkDigit % 11L;
        return lastDigit == checkDigit;
    }

    private long removeLastDigit(long number, long digit){
        return (number - digit) / 10L;
    }

    @Override
    public void initialize(ValidCDV constraintAnnotation) {
    }

}

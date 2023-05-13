package appointments;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import appointments.service.CdvValidator;

public class CdvValidatorTest {

    @Test
    void validateCheckDigit() {
        CdvValidator validator = new CdvValidator();

        assertFalse(validator.checkCdv(0L));
        assertFalse(validator.checkCdv(10_000_000_000L));
        assertFalse(validator.checkCdv(999_999_999L));
        assertFalse(validator.checkCdv(1_000_000_000L));

        assertTrue(validator.checkCdv(9_999_999_999L));
        assertTrue(validator.checkCdv(1_000_000_001L));
        assertTrue(validator.checkCdv(7_377_171_472L));
        assertTrue(validator.checkCdv(7_397_633_064L));
    }
}

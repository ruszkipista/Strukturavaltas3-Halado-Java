package flights.exception;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class AirplaneAlreadyScheduledForDayException extends RuntimeException {
    private long airplaneId;
    private LocalDate flightDate;

    public AirplaneAlreadyScheduledForDayException(long airplaneId, LocalDate flightDate, Throwable cause) {
        super(String.format("Flight is not free on %s", flightDate), cause);
        this.airplaneId = airplaneId;
        this.flightDate = flightDate;
    }

}
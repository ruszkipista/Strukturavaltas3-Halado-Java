package flights.exception;

import lombok.Getter;

@Getter
public class AirplaneNotFoundException extends RuntimeException {
    private long id;

    public AirplaneNotFoundException(long id) {
        this(id, null);
    }

    public AirplaneNotFoundException(long id, Throwable cause) {
        super(String.format("Airplane not found with id:%d",id), cause);
        this.id = id;
    }

}

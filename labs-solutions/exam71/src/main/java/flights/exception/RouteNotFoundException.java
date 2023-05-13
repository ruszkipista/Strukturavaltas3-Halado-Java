package flights.exception;

import lombok.Getter;

@Getter
public class RouteNotFoundException extends RuntimeException {
    private long id;

    public RouteNotFoundException(long id) {
        this(id, null);
    }

    public RouteNotFoundException(long id, Throwable cause) {
        super(String.format("Route not found with id: %d",id), cause);
        this.id = id;
    }
}

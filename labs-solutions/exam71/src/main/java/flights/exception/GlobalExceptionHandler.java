package flights.exception;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(AirplaneNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail resourceAirplaneNotFound(Exception e){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problem.setType(URI.create("airplanes/not-found"));
        return problem;
    }

    @ExceptionHandler(RouteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail resourceRouteNotFound(Exception e){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problem.setType(URI.create("routes/not-found"));
        return problem;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, AirplaneAlreadyScheduledForDayException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail requestDataInvalid(Exception e){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
            // e.getFieldError().getDefaultMessage()
            // e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        problem.setType(URI.create("airplanes/not-valid"));
        return problem;
    }

}

package training360.guinessapp.exceptionhandling;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class CanNotBeatRecordException extends AbstractThrowableProblem {

    public CanNotBeatRecordException() {
        super(URI.create("guinessapp/can-not-beat"), "Can not beat", Status.BAD_REQUEST);
    }
}

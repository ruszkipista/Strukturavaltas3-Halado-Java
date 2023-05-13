package training360.guinessapp.exceptionhandling;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class WorldRecordNotFoundException extends AbstractThrowableProblem {

    public WorldRecordNotFoundException() {
        super(URI.create("guinessapp/world-record-not-found"), "World record not found", Status.NOT_FOUND);
    }
}

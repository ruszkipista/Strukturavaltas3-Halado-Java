package training360.guinessapp.exceptionhandling;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class RecorderNotFoundException extends AbstractThrowableProblem {

    public RecorderNotFoundException() {
        super(URI.create("guinessapp/recorder-not-found"), "Recorder not found", Status.NOT_FOUND);
    }
}

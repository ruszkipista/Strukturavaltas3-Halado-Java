package sportresults.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class AthleteNotFoundException extends AbstractThrowableProblem {
    public AthleteNotFoundException(Long id) {
        super(URI.create("/athlete-not-found"),
                "Athlete not found",
                Status.NOT_FOUND,
                String.format("Athlete not found with id: %d",id));
    }
}


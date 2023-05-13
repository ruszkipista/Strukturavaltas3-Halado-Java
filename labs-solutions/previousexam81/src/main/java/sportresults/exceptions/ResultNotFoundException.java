package sportresults.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class ResultNotFoundException extends AbstractThrowableProblem {
    public ResultNotFoundException(Long id) {
        super(URI.create("/result-not-found"),
                "Result not found",
                Status.NOT_FOUND,
                String.format("Result not found with id: %d",id));
    }
}

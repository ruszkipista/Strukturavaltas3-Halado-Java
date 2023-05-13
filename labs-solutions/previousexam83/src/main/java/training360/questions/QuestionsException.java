package training360.questions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class QuestionsException extends AbstractThrowableProblem {

    public QuestionsException(String type, String message, Status status) {
        super(URI.create(type), message, status);
    }
}

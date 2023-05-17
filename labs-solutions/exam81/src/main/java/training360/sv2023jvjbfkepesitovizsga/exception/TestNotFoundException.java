package training360.sv2023jvjbfkepesitovizsga.exception;

public class TestNotFoundException extends RuntimeException {

    public TestNotFoundException(long id) {
        super(String.format("Test not found with id: %d",id));
    }
    
}

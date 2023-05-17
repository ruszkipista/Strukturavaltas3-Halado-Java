package training360.sv2023jvjbfkepesitovizsga.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(long id) {
        super(String.format("Student not found with id: %d",id));
    }
    
}

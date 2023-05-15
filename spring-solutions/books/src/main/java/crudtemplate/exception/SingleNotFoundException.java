package crudtemplate.exception;

public class SingleNotFoundException extends RuntimeException {

    public SingleNotFoundException(long id) {
        super(String.format("Entity not found with id %d",id));
    }
    
}

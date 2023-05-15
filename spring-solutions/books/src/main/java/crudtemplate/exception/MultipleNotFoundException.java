package crudtemplate.exception;

public class MultipleNotFoundException extends RuntimeException {

    public MultipleNotFoundException(long id) {
        super(String.format("Entity not found with id %d",id));
    }
    
}

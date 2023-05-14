package crudtemplate.exception;

public class MultipleNotFoundException extends RuntimeException {
    private long id;

    public MultipleNotFoundException(long id) {
        this(id, null);
    }

    public MultipleNotFoundException(long id, Throwable cause) {
        super(String.format("Entity not found with id %d",id), cause);
        this.id = id;
    }

    public long getId() {
        return this.id;
    }
    
}

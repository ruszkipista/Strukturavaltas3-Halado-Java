package crudtemplate.exception;

public class SingleNotFoundException extends RuntimeException {
    private long id;

    public SingleNotFoundException(long id) {
        this(id, null);
    }

    public SingleNotFoundException(long id, Throwable cause) {
        super(String.format("Entity not found with id %d",id), cause);
        this.id = id;
    }

    public long getId() {
        return this.id;
    }
    
}

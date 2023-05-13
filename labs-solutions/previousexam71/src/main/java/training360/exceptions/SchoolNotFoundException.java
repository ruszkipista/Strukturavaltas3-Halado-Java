package training360.exceptions;

public class SchoolNotFoundException extends RuntimeException {
    private long id;

    public SchoolNotFoundException(long id) {
            this(id, null);
    }

    public SchoolNotFoundException(long id, Throwable cause) {
            super(String.format("School not found with id: %d", id), cause);
            this.id = id;
    }

    public long getId() {
            return this.id;
    }
}

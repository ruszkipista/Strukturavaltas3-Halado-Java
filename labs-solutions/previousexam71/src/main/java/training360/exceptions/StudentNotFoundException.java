package training360.exceptions;

public class StudentNotFoundException extends RuntimeException {
        private long id;

        public StudentNotFoundException(long id) {
                this(id, null);
        }

        public StudentNotFoundException(long id, Throwable cause) {
                super(String.format("Student not found with id: %d", id), cause);
                this.id = id;
        }

        public long getId() {
                return this.id;
        }
}

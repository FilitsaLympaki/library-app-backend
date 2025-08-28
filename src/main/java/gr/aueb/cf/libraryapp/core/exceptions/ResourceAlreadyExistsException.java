package gr.aueb.cf.libraryapp.core.exceptions;

public class ResourceAlreadyExistsException extends GenericException {
    private static final String DEFAULT_CODE = "AlreadyExists";

    public ResourceAlreadyExistsException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}

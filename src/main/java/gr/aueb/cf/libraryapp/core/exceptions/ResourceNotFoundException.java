package gr.aueb.cf.libraryapp.core.exceptions;


public class ResourceNotFoundException extends GenericException {
    private static final String DEFAULT_CODE = "NotFound";

    public ResourceNotFoundException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}

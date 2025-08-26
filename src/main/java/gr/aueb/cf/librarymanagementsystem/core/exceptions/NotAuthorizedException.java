package gr.aueb.cf.librarymanagementsystem.core.exceptions;

public class NotAuthorizedException extends GenericException {

    private static final String DEFAULT_CODE = "NotAuthorized";

    public NotAuthorizedException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}

package technical.test.api.exception;

public class ProductAlreadyExistsException extends Exception {
    public ProductAlreadyExistsException(String s) {
        super(s);
    }
}

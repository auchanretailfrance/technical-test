package technical.test.api.exception;

public class ProductAlreadyExistsException extends RuntimeException {

	// renvoie un message d'erreur lorsqu'un produit existe déjà
	public ProductAlreadyExistsException(String message) {
		super(message);
	}
}

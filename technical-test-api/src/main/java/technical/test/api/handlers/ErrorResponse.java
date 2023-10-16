package technical.test.api.handlers;

import java.util.List;

public record ErrorResponse(int statusCode, String statusMessage, List<String> errorsDescription) {

}

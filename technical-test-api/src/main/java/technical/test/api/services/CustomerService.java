package technical.test.api.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.dto.CustomerDto;

public interface CustomerService {

    Mono<CustomerDto> getCustomer(String customerId);

    Flux<CustomerDto> getAllCustomers();

    Mono<CustomerDto> storeCustomer(CustomerDto customerDto);

}

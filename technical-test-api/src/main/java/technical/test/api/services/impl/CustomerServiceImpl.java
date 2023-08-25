package technical.test.api.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.dto.CustomerDto;
import technical.test.api.entity.Customer;
import technical.test.api.mapping.CustomerMapper;
import technical.test.api.repository.CustomerRepository;
import technical.test.api.services.CustomerService;


@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Mono<CustomerDto> getCustomer(String customerId) {
        return customerRepository.findById(customerId).map(CustomerMapper::entityToDto);
    }

    @Override
    public Flux<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().map(customer -> CustomerMapper.entityToDto(customer));
    }

    @Override
    public Mono<CustomerDto> storeCustomer(CustomerDto customerDto) {
        Mono<Customer> mono = customerRepository.save(CustomerMapper.dtoToEntity(customerDto));
        return mono.map(customer -> CustomerMapper.entityToDto(customer));
    }
}

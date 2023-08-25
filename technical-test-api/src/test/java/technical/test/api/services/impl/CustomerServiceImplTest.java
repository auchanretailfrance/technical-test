package technical.test.api.services.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import technical.test.api.dto.CustomerDto;
import technical.test.api.entity.Customer;
import technical.test.api.mapping.CustomerMapper;
import technical.test.api.repository.CustomerRepository;
import technical.test.api.services.CustomerService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void getCustomer() {
        Customer customer = new Customer( );
        customer.setClientId("clientId");

        when(customerRepository.findById(customer.getClientId())).thenReturn(Mono.just(customer));

        final Mono<CustomerDto> customerDtoMono = customerService.getCustomer(customer.getClientId( ));

        Assertions.assertThat(customerDtoMono).isNotNull();
        StepVerifier.create(customerDtoMono)
                .expectNextMatches(customerDto ->  "clientId".equals(customerDto.getClientId())).expectComplete().verify();


    }

    @Test
    void getAllCustomers() {
        Customer customer = new Customer();
        customer.setFirstName("Jean");
        Customer customer2 = new Customer();
        customer2.setFirstName("Paul");
        Customer customer3 = new Customer();
        customer3.setFirstName("Pierre");

        when(customerRepository.findAll()).thenReturn(Flux.just(customer, customer2, customer3));

        Flux<CustomerDto> fluxCustomerDto = customerService.getAllCustomers();

        StepVerifier.create(fluxCustomerDto)
                .expectNextMatches(customerDto -> "Jean".equals(customerDto.getFirstName()))
                .expectNextMatches(customerDto -> "Paul".equals(customerDto.getFirstName()))
                .expectNextMatches(customerDto -> "Pierre".equals(customerDto.getFirstName()))
                .expectComplete()
                .verify();
    }

    @Test
    void storeCustomer() {
        Customer customer = new Customer( );
        customer.setFirstName("Henry");

        when(customerRepository.save(customer)).thenReturn(Mono.just(customer));
        final Mono<CustomerDto> customerDtoMono = customerService.storeCustomer(CustomerMapper.entityToDto(customer));

        Assertions.assertThat(customerDtoMono).isNotNull();
        StepVerifier.create(customerDtoMono)
                .expectNextMatches(customerDto -> "Henry".equals(customerDto.getFirstName())).verifyComplete();

    }
}
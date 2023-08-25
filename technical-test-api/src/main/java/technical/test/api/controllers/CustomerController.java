package technical.test.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.dto.CustomerDto;
import technical.test.api.services.CustomerService;


@RestController
@RequestMapping( value = "/customers" )
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping( value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CustomerDto> getCustomer(@PathVariable String id) {
    return customerService.getCustomer(id);
    }

    @CrossOrigin
    @GetMapping( produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping( produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerDto> storeCustomer(@RequestBody CustomerDto customerDto){
        return customerService.storeCustomer(customerDto);

    }

}

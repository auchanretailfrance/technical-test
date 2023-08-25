package technical.test.api.mapping;

import technical.test.api.dto.CustomerDto;
import technical.test.api.entity.Customer;

public interface CustomerMapper {

    static Customer dtoToEntity(CustomerDto customerDto){
        Customer customer = new Customer( );
        customer.setClientId(customerDto.getClientId());
        customer.setFavoriteShop(customerDto.getFavoriteShop());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setLastVisit(customerDto.getLastVisit());

        return customer;
    }

    static CustomerDto entityToDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setClientId(customer.getClientId());
        customerDto.setFavoriteShop(customer.getFavoriteShop());
        customerDto.setLastName(customer.getLastName());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastVisit(customer.getLastVisit());

        return customerDto;
    }

}

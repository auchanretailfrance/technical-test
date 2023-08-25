package technical.test.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.dto.CustomerDto;
import technical.test.api.services.CustomerService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = {CustomerController.class})
@EnableWebMvc
@AutoConfigureMockMvc
public final class CustomerControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper( ).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build( );
    }

    @Test
    void getCustomer() throws Exception {
        final CustomerDto customerDto = new CustomerDto( );
        customerDto.setClientId("clientId");
        when(customerService.getCustomer(customerDto.getClientId( ))).thenReturn(Mono.just(customerDto));
        mockMvc.perform(get("/customers/" + customerDto.getClientId( )))
                .andExpect(status( ).isOk( ));

    }

    @Test
    void getAllCustomers() throws Exception {
        final CustomerDto customerDto = new CustomerDto( );
        when(customerService.getAllCustomers( )).thenReturn(Flux.just(customerDto, customerDto, customerDto));
        mockMvc.perform(get("/customers/"))
                .andExpect(status( ).isOk( ));
    }

    @Test
    void storeCustomer() throws Exception {
        final CustomerDto customerDto = new CustomerDto( );
        when(customerService.storeCustomer(customerDto)).thenReturn(Mono.just(customerDto));

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDto)))
                .andExpect(status( ).isCreated( ));

    }
}
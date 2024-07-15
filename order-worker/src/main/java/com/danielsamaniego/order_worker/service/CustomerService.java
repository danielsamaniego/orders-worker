package com.danielsamaniego.order_worker.service;

import com.danielsamaniego.order_worker.dto.CustomerGQLRequestDTO;
import com.danielsamaniego.order_worker.dto.QueryDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.danielsamaniego.order_worker.domain.Customer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CustomerService implements ICustomerService {

    private final WebClient webClient;


    public CustomerService(@Qualifier("customersWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Customer> getCustomerByIdentification(String identificationNumber) {
        System.out.println(identificationNumber);
        String query = "query CustomerByIdentificationNumber { " +
                "customerByIdentificationNumber(identificationNumber: \""+identificationNumber+"\") " +
                "{ email id identificationNumber name } }";
        QueryDTO customerQuery = new QueryDTO();
        customerQuery.setQuery(query);
        return webClient.post()
                .uri("/graphql")
                .bodyValue(customerQuery)
                .retrieve()
                .bodyToMono(CustomerGQLRequestDTO.class)
                .map(response -> response.getData().getCustomerByIdentificationNumber());
    }
}
package com.talentreadiness.consumerelasticsearch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.talentreadiness.consumerelasticsearch.model.CustomerES;
import com.talentreadiness.consumerelasticsearch.repository.CustomerRepositoryES;
import com.talentreadiness.database.dto.MessageDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@EnableKafka
public class CustomerServiceES {

    private final CustomerRepositoryES customerRepositoryES;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceES.class);

    @KafkaListener(topics = "topic-customer", groupId = "consumergroup")
    public void manageCustomer(MessageDTO messageDTO) {
        LOGGER.info("Message Received -> {}", messageDTO);
        String method = messageDTO.getMethod();
        CustomerES data = new CustomerES();
        data.setId(messageDTO.getCustomer().getId());
        data.setName(messageDTO.getCustomer().getName());
        data.setAge(messageDTO.getCustomer().getAge());
        LOGGER.info("Customer Ready -> {}", data);
        if (method.equals("POST") || method.equals("PUT")) {
            customerRepositoryES.save(data);
        } else if (method.equals("DELETE")) {
            // customerRepositoryES.deleteById(data.getId());
        }
    }

    public Iterable<CustomerES> getAllCustomerES() {
        return customerRepositoryES.findAll();
    }

    public void deleteElasticsearc() {
        customerRepositoryES.deleteAll();
    }
}

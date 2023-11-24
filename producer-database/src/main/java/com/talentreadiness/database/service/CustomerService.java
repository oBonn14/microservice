package com.talentreadiness.database.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.talentreadiness.database.dto.CustomerDTO;
import com.talentreadiness.database.dto.MessageDTO;
import com.talentreadiness.database.model.Customer;
import com.talentreadiness.database.repository.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;
    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;

    public Page<Customer> getAllCustomer(Pageable pageable, String search) {
        return customerRepository.findCustomerAll(pageable, search.toLowerCase());
    }

    public List<CustomerDTO> getListCustomer(Page<Customer> customer) {
        return customer.getContent().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private CustomerDTO convertToDto(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .age(customer.getAge())
                .build();
    }

    @Transactional
    public Customer storeCustomer(Customer customerDTO) {
        try {
            Customer data = customerRepository.save(customerDTO);

            MessageDTO messageDTO = new MessageDTO("POST", data);
            LOGGER.info("Request To Store Data -> {}", messageDTO);

            sendMessageToKafka(messageDTO);

            return data;
        } catch (Exception ex) {
            log.error("Error storing customer data", ex);
            throw new RuntimeException("Error storing customer data", ex);
        }
    }

    @Transactional
    public Customer updateCustomer(Long id, Customer customerDTO) {
        try {
            Customer customerUpdate = new Customer();
            Customer customerId = customerRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Id Customer Not Found"));
            if (customerId != null) {
                customerUpdate = customerRepository.save(customerDTO);
            }
            MessageDTO message = new MessageDTO("PUT", customerUpdate);
            LOGGER.info("Request To Update Data -> {}", message);
            sendMessageToKafka(message);

            return customerUpdate;
        } catch (Exception ex) {
            log.info(ex.getMessage());
            throw ex;
        }
    }

    @Transactional
    public void deleteCustomer(Long id) {
        try {
            Customer customerId = customerRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Id Customer Not Found"));

            MessageDTO message = new MessageDTO("DELETE", customerId);
            LOGGER.info("Request To Delete Data -> {}", message);
            sendMessageToKafka(message);

            customerRepository.delete(customerId);
        } catch (Exception ex) {
            log.info(ex.getMessage());
            throw ex;
        }
    }

    private void sendMessageToKafka(MessageDTO messageDTO) {
        Message<MessageDTO> message = MessageBuilder
                .withPayload(messageDTO)
                .setHeader(KafkaHeaders.TOPIC, "topic-customer")
                .build();
        kafkaTemplate.send(message);
    }

}

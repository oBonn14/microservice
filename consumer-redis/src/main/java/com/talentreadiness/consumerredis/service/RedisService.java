package com.talentreadiness.consumerredis.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.talentreadiness.database.dto.CustomerDTO;
import com.talentreadiness.database.dto.MessageDTO;
import com.talentreadiness.database.model.Customer;
import com.talentreadiness.database.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RedisService {

    private final RedisTemplate redisTemplate;
    private final CustomerRepository customerRepository;
    private static final String KEY = "CUSTOMER";
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisService.class);

    @KafkaListener(topics = "topic-customer", groupId = "consumergroup")
    public void manageCustomer(MessageDTO messageDTO) {

        LOGGER.info("Message Received -> {}", messageDTO);
        String method = messageDTO.getMethod();
        CustomerDTO data = new CustomerDTO();

        data.setId(messageDTO.getCustomer().getId());
        data.setName(messageDTO.getCustomer().getName());
        data.setAge(messageDTO.getCustomer().getAge());
        if (method.equals("POST") || method.equals("PUT")) {
            redisTemplate.opsForHash().put(KEY, data.getId(), data);
        } else if (method.equals("DELETE")) {
            redisTemplate.opsForHash().delete(KEY, data.getId());
        }
    }

    public List<CustomerDTO> getAllRedisCustomer() {
        List<CustomerDTO> customerList = redisTemplate.opsForHash().values(KEY);
        if (customerList.isEmpty()) {
            return new ArrayList<CustomerDTO>();
        }
        return customerList;
    }

}

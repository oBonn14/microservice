package com.talentreadiness.consumerredis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentreadiness.consumerredis.service.RedisService;
import com.talentreadiness.database.dto.CustomerDTO;
import com.talentreadiness.database.model.Customer;
import com.talentreadiness.database.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RedisController {

    private final RedisService redisService;
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<Object> getAllCustomerRedis(Pageable pageable, String search) {
        Page<Customer> customer = redisService.getAllCustomer(pageable, search);
        List<CustomerDTO> data = customerService.getListCustomer(customer);
        return ResponseEntity.ok(PagedModel.of(data,
                new PageMetadata(customer.getNumber(), customer.getSize(), customer.getNumberOfElements())));
    }
}

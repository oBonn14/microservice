package com.talentreadiness.consumerredis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
    public ResponseEntity<?> getAllRedisCustomerData() {
        List<CustomerDTO> customerList = redisService.getAllRedisCustomer();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Customer loaded successfully");
        response.put("data", customerList);
        return ResponseEntity.ok(response);
    }
}

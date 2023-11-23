package com.talentreadiness.consumerelasticsearch.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentreadiness.consumerelasticsearch.model.CustomerES;
import com.talentreadiness.consumerelasticsearch.service.CustomerServiceES;
import com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/elasticsearch")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerControllerES {

    private final CustomerServiceES customerServiceES;

    @GetMapping("/getAllCustomerES")
    public ResponseEntity<Object> getAllESCustomerData() {
        Iterable<CustomerES> customerList = customerServiceES.getAllCustomerES();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Customer loaded successfully");
        response.put("data", customerList);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteElasticsearch")
    public ResponseEntity<Object> deleteElasticsearch() {
        customerServiceES.deleteElasticsearc();
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonApiModelBuilder
                        .jsonApiModel()
                        .build());
    }
}

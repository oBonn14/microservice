package com.talentreadiness.database.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentreadiness.database.dto.CustomerDTO;
import com.talentreadiness.database.model.Customer;
import com.talentreadiness.database.service.CustomerService;
import com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<Object> getCustomer(Pageable pageable, String search) {
        Page<Customer> data = customerService.getAllCustomer(pageable, search);
        List<CustomerDTO> customerDTO = customerService.getListCustomer(data);
        return ResponseEntity.ok(PagedModel.of(customerDTO,
                new PageMetadata(data.getSize(), data.getNumber(), data.getTotalElements())));
    }

    @PostMapping("/createCustomer")
    public ResponseEntity<Object> createCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            var o = customerService.storeCustomer(customerDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(JsonApiModelBuilder
                            .jsonApiModel()
                            .model(EntityModel.of(o))
                            .build());
        } catch (Exception ex) {
            log.info(ex.getMessage());
            throw ex;
        }
    }
}

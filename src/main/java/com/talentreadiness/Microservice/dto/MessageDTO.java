package com.talentreadiness.Microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageDTO {
    private String method;
    private CustomerDTO customer;
}

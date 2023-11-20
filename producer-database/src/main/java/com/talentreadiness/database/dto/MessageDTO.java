package com.talentreadiness.database.dto;

import com.talentreadiness.database.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String method;
    private Customer customer;
}

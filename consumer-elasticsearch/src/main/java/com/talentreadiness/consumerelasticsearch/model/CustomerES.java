package com.talentreadiness.consumerelasticsearch.model;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "customer")
public class CustomerES {
    private Long id;
    private String name;
    private Integer age;
}

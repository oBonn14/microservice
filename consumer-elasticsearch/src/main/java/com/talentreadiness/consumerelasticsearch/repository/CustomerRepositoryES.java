package com.talentreadiness.consumerelasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.talentreadiness.consumerelasticsearch.model.CustomerES;

@Repository
public interface CustomerRepositoryES extends ElasticsearchRepository<CustomerES, Long> {

}

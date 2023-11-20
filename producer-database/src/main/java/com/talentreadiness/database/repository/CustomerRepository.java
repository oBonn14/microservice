package com.talentreadiness.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.talentreadiness.database.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE (:search IS NULL OR LOWER (c.name) LIKE %:search% OR LOWER(c.age) LIKE %:search% )")
    Page<Customer> findCustomerAll(Pageable pageable, String search);
}

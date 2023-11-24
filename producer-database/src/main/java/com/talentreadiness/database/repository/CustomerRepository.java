package com.talentreadiness.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.talentreadiness.database.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE (:search IS NULL OR LOWER (c.name) LIKE %:search% )")
    Page<Customer> findCustomerAll(Pageable pageable, String search);
}

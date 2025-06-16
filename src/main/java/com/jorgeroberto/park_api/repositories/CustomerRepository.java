package com.jorgeroberto.park_api.repositories;

import com.jorgeroberto.park_api.entities.Customer;
import com.jorgeroberto.park_api.repositories.projection.CustomerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c")
    Page<CustomerProjection> findAllPageable(Pageable pageable);

    Customer findByUserId(Long id);
}

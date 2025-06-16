package com.jorgeroberto.park_api.services;

import com.jorgeroberto.park_api.entities.Customer;
import com.jorgeroberto.park_api.exceptions.CpfUniqueViolationException;
import com.jorgeroberto.park_api.repositories.CustomerRepository;
import com.jorgeroberto.park_api.repositories.projection.CustomerProjection;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Customer save(Customer obj) {
        try {
            return customerRepository.save(obj);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(
                    String.format("CPF %s ja cadastrado no sistema", obj.getCpf())
            );
        }
    }

    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Customer with id=%s not found", id))
        );
    }


    @Transactional(readOnly = true)
    public Page<CustomerProjection> findAll(Pageable pageable) {
        return customerRepository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Customer findByUserId(Long id) {
        return customerRepository.findByUserId(id);
    }
}

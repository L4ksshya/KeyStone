package com.key_stone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.key_stone.Entity.Customer;
import com.key_stone.dto.CustomerRequestDTO;
import com.key_stone.dto.CustomerResponseDTO;
import com.key_stone.repository.CustomerRepository;

import com.key_stone.exception.CustomerNotFoundException;
import com.key_stone.exception.CustomerEmailAlreadyExistsException;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(
            CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO request) {

        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomerEmailAlreadyExistsException(
                    "Customer with email already exists: " + request.getEmail());
        }

        Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());

        Customer savedCustomer = customerRepository.save(customer);

        return new CustomerResponseDTO(
                savedCustomer.getId(),
                savedCustomer.getName(),
                savedCustomer.getEmail(),
                savedCustomer.getPhone(),
                savedCustomer.getAddress()
        );
    }
    public List<CustomerResponseDTO> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(customer -> new CustomerResponseDTO(
                        customer.getId(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getPhone(),
                        customer.getAddress()
                ))
                .toList();
    }
    public CustomerResponseDTO getCustomerById(Long id) {

        Customer customer =
                customerRepository.findById(id)
                .orElseThrow(() ->
                new CustomerNotFoundException("Customer not found with id: " + id));

        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress()
        );
    }
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO request) {

        Customer customer = customerRepository.findById(id)
        		.orElseThrow(() ->
        	    new CustomerNotFoundException("Customer not found with id: " + id));

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());

        Customer updatedCustomer = customerRepository.save(customer);

        return new CustomerResponseDTO(
                updatedCustomer.getId(),
                updatedCustomer.getName(),
                updatedCustomer.getEmail(),
                updatedCustomer.getPhone(),
                updatedCustomer.getAddress()
        );
    }
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
        		.orElseThrow(() ->
        	    new CustomerNotFoundException("Customer not found with id: " + id));

        customerRepository.delete(customer);
    }
}
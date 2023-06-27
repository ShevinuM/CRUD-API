package com.shevinum;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void addCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer(
                customerRequest.name(),
                customerRequest.email(),
                customerRequest.age()
        );
        customerRepository.save(customer);
    }

    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    public void updateCustomer(Integer id, CustomerRequest customerRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            customer.setName(customerRequest.name());
            customer.setEmail(customerRequest.email());
            customer.setAge(customerRequest.age());
            customerRepository.save(customer);
        } else {
            // Handle the case where the customer was not found
        }
    }
}

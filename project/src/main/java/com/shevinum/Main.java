package com.shevinum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class Main {

    private CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/getCustomers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
    record CustomerRequest(
            String name,
            String email,
            Integer age
    ){
    }

    @PostMapping("/addCustomer")
    public void addCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer customer = new Customer(
                customerRequest.name(),
                customerRequest.email(),
                customerRequest.age()
        );
        customerRepository.save(customer);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public void deleteCustomer(@PathVariable("id") Integer id) {
        customerRepository.deleteById(id);
    }

    @PutMapping("/updateCustomer/{id}")
    public void updateCustomer(@PathVariable("id") Integer id,
                               @RequestBody CustomerRequest customerRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        Customer customer = optionalCustomer.get();
        customer.setName(customerRequest.name());
        customer.setEmail(customerRequest.email());
        customer.setAge(customerRequest.age());
        customerRepository.save(customer);
    }

}

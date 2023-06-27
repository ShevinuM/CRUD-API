package com.shevinum;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getCustomers")
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping("/addCustomer")
    public void addCustomer(@RequestBody CustomerRequest customerRequest) {
        customerService.addCustomer(customerRequest);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public void deleteCustomer(@PathVariable("id") Integer id) {
        customerService.deleteCustomer(id);
    }

    @PutMapping("/updateCustomer/{id}")
    public void updateCustomer(@PathVariable("id") Integer id,
                               @RequestBody CustomerRequest customerRequest) {
        customerService.updateCustomer(id, customerRequest);
    }
}

package com.csi.controller;

import com.csi.exception.RecordNotFoundException;
import com.csi.model.Customer;
import com.csi.service.CustomerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @PostMapping("/signup")
    public ResponseEntity<Customer> signUp(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(customerServiceImpl.signUp(customer), HttpStatus.CREATED);
    }

    @GetMapping("/signin/{custEmailId}/{custPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String custEmailId, @PathVariable String custPassword) {
        return ResponseEntity.ok(customerServiceImpl.signIn(custEmailId, custPassword));
    }

    @GetMapping("/findbyid/{custId}")
    public ResponseEntity<Optional<Customer>> findById(@PathVariable int custId) {
        return ResponseEntity.ok(customerServiceImpl.findById(custId));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(customerServiceImpl.findAll());
    }

    @PutMapping("/update/{custId}")
    public ResponseEntity<Customer> update(@PathVariable int custId, @Valid @RequestBody Customer customer) {
        Customer customer1 = customerServiceImpl.findById(custId).orElseThrow(() -> new RecordNotFoundException("Customer ID does not exist"));

        customer1.setCustDOB(customer.getCustDOB());
        customer1.setCustEmailId(customer.getCustEmailId());
        customer1.setCustName(customer.getCustName());
        customer1.setCustAddress(customer.getCustAddress());
        customer1.setCustPassword(customer.getCustPassword());
        customer1.setCustAccountBalance(customer.getCustAccountBalance());

        customer1.setCustContactNumber(customer.getCustContactNumber());

        return new ResponseEntity<>(customerServiceImpl.update(customer1), HttpStatus.CREATED);
    }

    @DeleteMapping("/deletebyid/{custId}")
    public ResponseEntity<String> deleteById(@PathVariable int custId) {
        customerServiceImpl.deleteById(custId);
        return ResponseEntity.ok("Data Deleted Successfully");
    }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<Customer>> sortByName() {
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().sorted(Comparator.comparing(Customer::getCustName).reversed()).toList());
    }
    // All JDK 8 features
    //1. sort by- id, name, accbalance, dob
    //2. filter data- id, name, accbalance, dob, anyinput
    //3. create new API's Sum of Acc Bal
}

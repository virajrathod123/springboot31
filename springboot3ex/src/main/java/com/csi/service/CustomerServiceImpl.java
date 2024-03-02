package com.csi.service;

import com.csi.model.Customer;
import com.csi.repo.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl {

    @Autowired
    private CustomerRepo customerRepoImpl;

    public Customer signUp(Customer customer) {
        return customerRepoImpl.save(customer);
    }

    public boolean signIn(String custEmailId, String custPassword) {
        boolean status = false;

        Customer customer = customerRepoImpl.findByCustEmailIdAndCustPassword(custEmailId, custPassword);

        if (customer != null && customer.getCustEmailId().equals(custEmailId) && customer.getCustPassword().equals(custPassword)) {
            status = true;
        }

        return status;
    }

    @Cacheable(value = "custId")
    public Optional<Customer> findById(int custId) {

        log.info("@@@@@@@Fetch data from Database@@@@@@@@");
        return customerRepoImpl.findById(custId);
    }

    public List<Customer> findAll() {
        return customerRepoImpl.findAll();
    }

    public Customer update(Customer customer) {
        return customerRepoImpl.save(customer);
    }

    public void deleteById(int custId) {
        customerRepoImpl.deleteById(custId);
    }
}

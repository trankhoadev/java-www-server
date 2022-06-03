package com.se.spring.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.spring.entity.Customer;
import com.se.spring.service.CustomerService;

@RestController
@RequestMapping("/api")
public class DemoRestController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		List<Customer> list = customerService.getCustomers();

		return list;
	}

	@GetMapping("/customers/{customerID}")
	public Customer getCustomers(@PathVariable int customerID) {
		Customer customer = customerService.getCustomer(customerID);

		return customer;
	}

	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer customer) {
		customerService.saveCustomer(customer);
		return customer;
	}

	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer customer) {
		customerService.saveCustomer(customer);
		return customer;
	}

	@DeleteMapping("/customers/{customerID}")
	public String deleteCustomer(@PathVariable int customerID) {
		Customer temp = customerService.getCustomer(customerID);
		customerService.deleteCustomer(customerID);
		return "Deleted customer id - " + customerID;
	}

}

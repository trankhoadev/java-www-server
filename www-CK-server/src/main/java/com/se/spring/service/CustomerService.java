package com.se.spring.service;

import java.util.List;

import com.se.spring.entity.Customer;

public interface CustomerService {
	public List<Customer> getCustomers();

	public Customer getCustomer(int id);

	public void deleteCustomer(int id);

	void saveCustomer(Customer customer);

}

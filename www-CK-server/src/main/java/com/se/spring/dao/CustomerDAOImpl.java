package com.se.spring.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.se.spring.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<Customer> getCustomers() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Customer> query = currentSession.createQuery("FROM Customer", Customer.class);
		List<Customer> customers = query.getResultList();
		return customers;
	}

	@Override
	@Transactional
	public Customer getCustomer(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Customer customer = currentSession.get(Customer.class, id);
		return customer;
	}

	@Override
	@Transactional
	public void saveCustomer(Customer customer) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(customer);
	}

	@Override
	@Transactional
	public void deleteCustomer(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Customer customer = getCustomer(id);
		currentSession.delete(customer);
	}

}

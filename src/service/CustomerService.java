package service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import model.Customer;

public class CustomerService {
	private static final CustomerService CusService = new CustomerService();

	private List<Customer> customers = new LinkedList<Customer>();

	public static CustomerService getCusService() {
		return CusService;
	}

	public void addCustomer(String firstName, String lastName, String email) {
		customers.add(new Customer(firstName, lastName, email));

	}

	public Customer getCustomer(String customerEmail) {
		for (Customer e : customers) {
			if (e.getEmail().equals(customerEmail))
				return e;
		}
		return null;
	}

	public Collection<Customer> getAllCustomer() {
		return customers;
	}

}

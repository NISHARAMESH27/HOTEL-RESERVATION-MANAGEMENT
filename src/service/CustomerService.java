package service;

import model.Customer;
import java.util.*;

public class CustomerService {

    private static final CustomerService INSTANCE = new CustomerService();
    private final Map<String, Customer> customers = new HashMap<>();

    private CustomerService() {}

    public static CustomerService getInstance() {
        return INSTANCE;
    }

    public void addCustomer(String email, String first, String last) {
        customers.put(email.toLowerCase(),
                new Customer(first, last, email));
    }

    public Customer getCustomer(String email) {
        return customers.get(email.toLowerCase());
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}

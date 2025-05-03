package com.erp.Service.CustomerService;

import com.erp.Dto.Request.CustomerRequest;
import com.erp.Dto.Response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    /**
     * Creates a new customer record.
     * <p>
     * This method takes a {@link CustomerRequest} containing details like name, email, phone, and address,
     * and processes it to create a new customer.
     * </p>
     *
     * @param customerRequest The request object containing the customer’s details.
     * @return CustomerResponse A response containing information about the newly created customer.
     */
    CustomerResponse createCustomer(CustomerRequest customerRequest);

    /**
     * Updates an existing customer’s information.
     *
     * @param customerRequest The updated customer details.
     * @param id              The ID of the customer to update.
     * @return CustomerResponse The updated customer information or an error response if the update fails.
     */
    CustomerResponse updateCustomerInfo(CustomerRequest customerRequest, long id);

    /**
     * Retrieves a customer by their ID.
     *
     * @param customerId The ID of the customer to retrieve.
     * @return CustomerResponse The details of the customer, or an error response if not found.
     */
    CustomerResponse findByCustomerId(long customerId);

    /**
     * Deletes a customer by their ID.
     *
     * @param customerId The ID of the customer to delete.
     * @return CustomerResponse A response confirming successful deletion or indicating failure.
     */
    CustomerResponse deleteByCustomerId(long customerId);

    /**
     * Retrieves all customer records from the database.
     *
     * @return List<CustomerResponse> A list of all customers in the system.
     */
    List<CustomerResponse> getAllCustomer();
}

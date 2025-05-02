package com.erp.Service.CustomerService;

import com.erp.Dto.Request.CustomerRequest;
import com.erp.Dto.Response.CustomerResponse;
import com.erp.Exception.Customer.CustomerNotFoundException;
import com.erp.Mapper.Customer.CustomerMapper;
import com.erp.Model.Ledger;
import com.erp.Repository.Customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest){
        Ledger customer = customerMapper.maptoCustomer(customerRequest);
        customerRepository.save(customer);
        return customerMapper.mapToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomerInfo(CustomerRequest customerRequest, long id){
        Ledger customer = customerRepository.findById(id)
                .orElseThrow(()-> new CustomerNotFoundException("Customer Not Found! Invalid Id"));

        customerMapper.mapToCustomerEntity(customerRequest, customer);
        customerRepository.save(customer);
        return customerMapper.mapToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse findByCustomerId(long customerId) {
        Ledger customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer Not Found! Invalid Id"));
        return customerMapper.mapToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse deleteByCustomerId(long customerId){
        Ledger customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer Not Found! Invalid Id"));
        customerRepository.deleteById(customerId);
        return customerMapper.mapToCustomerResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomer(){
        List<Ledger> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::mapToCustomerResponse)
                .collect(Collectors.toList());
    }
}

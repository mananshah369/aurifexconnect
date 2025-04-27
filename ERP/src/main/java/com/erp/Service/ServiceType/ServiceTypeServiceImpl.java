package com.erp.Service.ServiceType;

import com.erp.Dto.Request.ServiceTypeRequest;
import com.erp.Dto.Response.ServiceTypeResponse;
import com.erp.Exception.Service_Exception.ServiceTypeNotFoundByIdException;
import com.erp.Mapper.ServiceType.ServiceTypeMapper;
import com.erp.Model.ServiceType;
import com.erp.Repository.ServiceType.ServiceTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceTypeServiceImpl implements ServiceTypeService{

    private final ServiceTypeRepository repository;
    private final ServiceTypeMapper serviceTypeMapper;

    @Override
    public ServiceTypeResponse addService(ServiceTypeRequest serviceTypeRequest) {
        ServiceType serviceType = serviceTypeMapper.mapToServiceType(serviceTypeRequest);
        repository.save(serviceType);
        return serviceTypeMapper.mapToServiceTypeResponse(serviceType);
    }

    @Override
    public ServiceTypeResponse updateById(ServiceTypeRequest serviceTypeRequest, long serviceId) {
        ServiceType serviceType = repository.findById(serviceId)
                .orElseThrow(()-> new ServiceTypeNotFoundByIdException("Id Invalid ! Service not found !"));

        serviceTypeMapper.mapToServiceTypeEntity(serviceTypeRequest,serviceType);
        repository.save(serviceType);
        return serviceTypeMapper.mapToServiceTypeResponse(serviceType);
    }

    @Override
    public List<ServiceTypeResponse> findByName(String name) {
        List<ServiceType> serviceTypeResponses = repository.findByServiceName(name);
        if(serviceTypeResponses.isEmpty()){
            throw new ServiceTypeNotFoundByIdException("Enter valid service name !!");
        }
        return serviceTypeMapper.mapToServiceTypeResponse(serviceTypeResponses);
    }

    @Override
    public ServiceTypeResponse findByServiceId(long serviceId) {
        ServiceType serviceType = repository.findById(serviceId)
                .orElseThrow(()-> new ServiceTypeNotFoundByIdException("Id Invalid ! Service not found !"));

        return serviceTypeMapper.mapToServiceTypeResponse(serviceType);
    }

    @Override
    public ServiceTypeResponse deleteByItemId(long itemId) {

            ServiceType serviceType = repository.findById(itemId)
                    .orElseThrow(() -> new ServiceTypeNotFoundByIdException("Service not found , invalid id "));
            repository.deleteById(itemId);
            return serviceTypeMapper.mapToServiceTypeResponse(serviceType);

    }
}

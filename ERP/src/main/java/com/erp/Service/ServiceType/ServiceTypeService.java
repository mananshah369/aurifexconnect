package com.erp.Service.ServiceType;

import com.erp.Dto.Request.ServiceTypeRequest;
import com.erp.Dto.Response.ServiceTypeResponse;
import com.erp.Exception.Service_Exception.ServiceTypeNotFoundByIdException;

import java.util.List;

public interface ServiceTypeService {

    /**
     * Adds a new service.
     *
     * @param serviceTypeRequest The request object containing the details of the service to be added.
     * @return A {@link ServiceTypeResponse} object containing the details of the added service.
     */
    ServiceTypeResponse addService(ServiceTypeRequest serviceTypeRequest);

    /**
     * Updates an existing service by its ID.
     *
     * @param serviceTypeRequest The request object containing the updated service details.
     * @param serviceId The ID of the service to be updated.
     * @return A {@link ServiceTypeResponse} object containing the details of the updated service.
     * @throws ServiceTypeNotFoundByIdException If the service with the given ID does not exist.
     */
    ServiceTypeResponse updateById(ServiceTypeRequest serviceTypeRequest, long serviceId);

    /**
     * Finds services by their name.
     *
     * @param name The name of the service(s) to search for.
     * @return A list of {@link ServiceTypeResponse} objects containing details of the services that match the name.
     * @throws ServiceTypeNotFoundByIdException If no services with the given name are found.
     */
    List<ServiceTypeResponse> findByName(String name);

    /**
     * Finds a service by its ID.
     *
     * @param serviceId The ID of the service to be found.
     * @return A {@link ServiceTypeResponse} object containing the details of the service.
     * @throws ServiceTypeNotFoundByIdException If no service with the given ID exists.
     */
    ServiceTypeResponse findByServiceId(long serviceId);

    /**
     * Deletes a service by its ID.
     *
     * @param itemId The ID of the service to be deleted.
     * @return A {@link ServiceTypeResponse} object containing the details of the deleted service.
     * @throws ServiceTypeNotFoundByIdException If no service with the given ID exists.
     */
    ServiceTypeResponse deleteByItemId(long itemId);
}

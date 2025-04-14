package com.erp.Mapper.ServiceType;

import com.erp.Dto.Request.ServiceTypeRequest;
import com.erp.Dto.Response.ServiceTypeResponse;
import com.erp.Model.ServiceType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ServiceTypeMapper {

    /**
     * Maps a {@link ServiceType} entity to a {@link ServiceTypeResponse}.
     *
     * @param services The {@link ServiceType} entity to be mapped.
     * @return A {@link ServiceTypeResponse} object containing the mapped details of the service.
     */
    ServiceTypeResponse mapToServiceTypeResponse(ServiceType services);

    /**
     * Maps a list of {@link ServiceType} entities to a list of {@link ServiceTypeResponse}.
     *
     * @param services A list of {@link ServiceType} entities to be mapped.
     * @return A list of {@link ServiceTypeResponse} objects containing the mapped details of the services.
     */
    List<ServiceTypeResponse> mapToServiceTypeResponse(List<ServiceType> services);

    /**
     * Maps a {@link ServiceTypeRequest} to a {@link ServiceType} entity.
     *
     * @param serviceTypeRequest The {@link ServiceTypeRequest} object containing the details of the service.
     * @return A {@link ServiceType} entity containing the mapped service details.
     */
    ServiceType mapToServiceType(ServiceTypeRequest serviceTypeRequest);

    /**
     * Maps a {@link ServiceTypeRequest} to an existing {@link ServiceType} entity, updating the entity with the request data.
     *
     * @param serviceTypeRequest The {@link ServiceTypeRequest} object containing the updated details of the service.
     * @param serviceType The existing {@link ServiceType} entity that will be updated.
     */
    void mapToServiceTypeEntity(ServiceTypeRequest serviceTypeRequest, @MappingTarget ServiceType serviceType);


}

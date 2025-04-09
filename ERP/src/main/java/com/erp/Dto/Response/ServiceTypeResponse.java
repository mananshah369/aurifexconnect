package com.erp.Dto.Response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceTypeResponse {
    private long serviceId;

    private String serviceName;

    private double servicePrices;
}

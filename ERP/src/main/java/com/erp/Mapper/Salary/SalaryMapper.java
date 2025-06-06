package com.erp.Mapper.Salary;

import com.erp.Dto.Response.SalaryResponse;
import com.erp.Model.Salary;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SalaryMapper {
    SalaryResponse mapToResponse(Salary salary);
    List<SalaryResponse> mapToResponseList(List<Salary> salaries);
}
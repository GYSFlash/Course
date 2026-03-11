package com.hotel.mapper;

import com.hotel.dto.ServiceRequestDTO;
import com.hotel.dto.ServiceResponseDTO;
import com.hotel.model.Service;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceResponseDTO toServiceResponseDTO(Service service);
    Service toService(ServiceRequestDTO serviceRequestDTO);
    List<ServiceResponseDTO> toServiceResponseDTOList(List<Service> services);
}

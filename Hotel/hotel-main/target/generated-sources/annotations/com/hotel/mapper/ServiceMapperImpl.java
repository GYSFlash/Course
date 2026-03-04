package com.hotel.mapper;

import com.hotel.dto.ClientResponseDTO;
import com.hotel.dto.ServiceRequestDTO;
import com.hotel.dto.ServiceResponseDTO;
import com.hotel.model.Client;
import com.hotel.model.Service;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-03T16:00:37+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (JetBrains s.r.o.)"
)
@Component
public class ServiceMapperImpl implements ServiceMapper {

    @Override
    public ServiceResponseDTO toServiceResponseDTO(Service service) {
        if ( service == null ) {
            return null;
        }

        ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();

        serviceResponseDTO.setClient( clientToClientResponseDTO( service.getClient() ) );
        serviceResponseDTO.setTypeService( service.getTypeService() );
        serviceResponseDTO.setServicePrice( service.getServicePrice() );
        serviceResponseDTO.setServiceName( service.getServiceName() );
        serviceResponseDTO.setId( service.getId() );
        serviceResponseDTO.setDuration( service.getDuration() );
        serviceResponseDTO.setDate( service.getDate() );

        return serviceResponseDTO;
    }

    @Override
    public Service toService(ServiceRequestDTO serviceRequestDTO) {
        if ( serviceRequestDTO == null ) {
            return null;
        }

        Service service = new Service();

        service.setTypeService( serviceRequestDTO.getTypeService() );
        service.setClient( clientResponseDTOToClient( serviceRequestDTO.getClient() ) );
        service.setDuration( serviceRequestDTO.getDuration() );
        service.setServiceName( serviceRequestDTO.getServiceName() );
        service.setServicePrice( serviceRequestDTO.getServicePrice() );
        service.setDate( serviceRequestDTO.getDate() );

        return service;
    }

    @Override
    public List<ServiceResponseDTO> toServiceResponseDTOList(List<Service> services) {
        if ( services == null ) {
            return null;
        }

        List<ServiceResponseDTO> list = new ArrayList<ServiceResponseDTO>( services.size() );
        for ( Service service : services ) {
            list.add( toServiceResponseDTO( service ) );
        }

        return list;
    }

    protected ClientResponseDTO clientToClientResponseDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();

        clientResponseDTO.setId( client.getId() );
        clientResponseDTO.setName( client.getName() );
        clientResponseDTO.setSurname( client.getSurname() );

        return clientResponseDTO;
    }

    protected Client clientResponseDTOToClient(ClientResponseDTO clientResponseDTO) {
        if ( clientResponseDTO == null ) {
            return null;
        }

        Client client = new Client();

        client.setId( clientResponseDTO.getId() );
        client.setName( clientResponseDTO.getName() );
        client.setSurname( clientResponseDTO.getSurname() );

        return client;
    }
}

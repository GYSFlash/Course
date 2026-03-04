package com.hotel.mapper;

import com.hotel.dto.ClientRequestDTO;
import com.hotel.dto.ClientResponseDTO;
import com.hotel.model.Client;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-03T16:00:36+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.14 (JetBrains s.r.o.)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientResponseDTO toClientDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();

        clientResponseDTO.setId( client.getId() );
        clientResponseDTO.setName( client.getName() );
        clientResponseDTO.setSurname( client.getSurname() );

        return clientResponseDTO;
    }

    @Override
    public Client toClient(ClientRequestDTO clientRequestDTO) {
        if ( clientRequestDTO == null ) {
            return null;
        }

        Client client = new Client();

        client.setDateOfBirth( clientRequestDTO.getDateOfBirth() );
        client.setGender( clientRequestDTO.getGender() );
        client.setName( clientRequestDTO.getName() );
        client.setSurname( clientRequestDTO.getSurname() );

        return client;
    }

    @Override
    public List<ClientResponseDTO> toClientDTOList(List<Client> clients) {
        if ( clients == null ) {
            return null;
        }

        List<ClientResponseDTO> list = new ArrayList<ClientResponseDTO>( clients.size() );
        for ( Client client : clients ) {
            list.add( toClientDTO( client ) );
        }

        return list;
    }
}

package com.hotel.mapper;

import com.hotel.dto.ClientRequestDTO;
import com.hotel.dto.ClientResponseDTO;
import com.hotel.model.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientResponseDTO toClientDTO(Client client);
    Client toClient(ClientRequestDTO clientRequestDTO);
    List<ClientResponseDTO> toClientDTOList(List<Client> clients);
}

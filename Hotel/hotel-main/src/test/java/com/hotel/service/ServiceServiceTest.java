package com.hotel.service;

import com.hotel.dto.ClientResponseDTO;
import com.hotel.dto.ServiceRequestDTO;
import com.hotel.dto.ServiceResponseDTO;
import com.hotel.exceptions.NoIllegalArgumentException;
import com.hotel.exceptions.NotFoundException;
import com.hotel.mapper.ServiceMapper;
import com.hotel.model.Client;
import com.hotel.model.Service;
import com.hotel.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ServiceMapper serviceMapper;

    @InjectMocks
    private ServiceServiceImpl serviceService;

    private ServiceRequestDTO requestDTO;
    private Service service;
    private ServiceResponseDTO responseDTO;
    private ClientResponseDTO client;
    private Client clientEntity;

    @BeforeEach
    void setUp() {
        client = new ClientResponseDTO();
        client.setId(1L);
        clientEntity = new Client();
        clientEntity.setId(1L);

        requestDTO = new ServiceRequestDTO();
        requestDTO.setClient(client);
        requestDTO.setServiceName("Массаж");
        requestDTO.setServicePrice(BigDecimal.valueOf(1000));
        requestDTO.setTypeService(Service.TypeService.OTHER);
        requestDTO.setDuration(Duration.ofHours(1));
        requestDTO.setDate(new Date());

        service = new Service();
        service.setId(1L);
        service.setServiceName("Массаж");
        service.setClient(clientEntity);

        responseDTO = new ServiceResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setServiceName("Массаж");
    }

    @Test
    void addServiceTest_success() {
        when(serviceMapper.toService(requestDTO)).thenReturn(service);

        serviceService.addService(requestDTO);

        verify(serviceRepository).create(service);
    }

    @Test
    void addServiceTest_fail() {
        requestDTO.setClient(null);

        assertThrows(NoIllegalArgumentException.class, () -> serviceService.addService(requestDTO));
        verify(serviceRepository, never()).create(any());
    }

    @Test
    void deleteServiceTest_success() {
        when(serviceRepository.deleteById(1L)).thenReturn(true);
        serviceService.deleteService(1L);

        verify(serviceRepository).deleteById(1L);
    }

    @Test
    void updateServiceTest_success() {
        when(serviceMapper.toService(requestDTO)).thenReturn(service);
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));
        when(serviceService.getServiceById(1L)).thenReturn(responseDTO);

        serviceService.updateService(1L, requestDTO);

        verify(serviceRepository).update(service);
    }

    @Test
    void updateServiceTest_fail() {
        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> serviceService.updateService(1L, requestDTO));
        verify(serviceRepository, never()).update(any());
    }

    @Test
    void getAllServicesTest_success() {
        when(serviceRepository.findAll()).thenReturn(List.of(service));
        when(serviceMapper.toServiceResponseDTOList(List.of(service))).thenReturn(List.of(responseDTO));

        List<ServiceResponseDTO> result = serviceService.getAllServices();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(serviceRepository).findAll();
    }

    @Test
    void getAllServicesTest_empty() {
        when(serviceRepository.findAll()).thenReturn(List.of());
        when(serviceMapper.toServiceResponseDTOList(List.of())).thenReturn(List.of());

        List<ServiceResponseDTO> result = serviceService.getAllServices();

        assertTrue(result.isEmpty());
    }

    @Test
    void getServiceByIdTest_success() {
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));
        when(serviceMapper.toServiceResponseDTO(service)).thenReturn(responseDTO);

        ServiceResponseDTO result = serviceService.getServiceById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getServiceByIdTest_fail() {
        when(serviceRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> serviceService.getServiceById(999L));
    }

    @Test
    void sortTest_success_byPrice() {
        List<ServiceResponseDTO> responseList = new ArrayList<>();
        responseList.add(responseDTO);

        when(serviceRepository.findAll()).thenReturn(List.of(service));
        when(serviceMapper.toServiceResponseDTOList(List.of(service))).thenReturn(responseList);

        List<ServiceResponseDTO> result = serviceService.sort("price");

        assertNotNull(result);
        verify(serviceRepository).findAll();
    }

    @Test
    void sortTest_success() {
        List<ServiceResponseDTO> responseList = new ArrayList<>();
        responseList.add(responseDTO);

        when(serviceRepository.findAll()).thenReturn(List.of(service));
        when(serviceMapper.toServiceResponseDTOList(List.of(service))).thenReturn(responseList);

        List<ServiceResponseDTO> result = serviceService.sort("date");

        assertNotNull(result);
    }

    @Test
    void sortTest_fail() {
        List<ServiceResponseDTO> responseList = new ArrayList<>();
        responseList.add(responseDTO);

        when(serviceRepository.findAll()).thenReturn(List.of(service));
        when(serviceMapper.toServiceResponseDTOList(List.of(service))).thenReturn(responseList);

        assertThrows(NoIllegalArgumentException.class, () -> serviceService.sort("invalid"));
    }

    @Test
    void sortTest_empty() {
        when(serviceRepository.findAll()).thenReturn(List.of());
        when(serviceMapper.toServiceResponseDTOList(List.of())).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> serviceService.sort("price"));
    }
}
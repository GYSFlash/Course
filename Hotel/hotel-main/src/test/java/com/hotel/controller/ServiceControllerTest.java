package com.hotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.dto.ClientResponseDTO;
import com.hotel.dto.ServiceRequestDTO;
import com.hotel.dto.ServiceResponseDTO;
import com.hotel.model.Service;
import com.hotel.service.ServiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ServiceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServiceService serviceService;

    @InjectMocks
    private ServiceController serviceController;

    private ObjectMapper objectMapper;
    private ServiceRequestDTO requestDTO;
    private ServiceResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(serviceController).build();

        ClientResponseDTO client = new ClientResponseDTO();
        client.setId(1L);


        requestDTO = new ServiceRequestDTO();
        requestDTO.setClient(client);
        requestDTO.setServiceName("Массаж");
        requestDTO.setServicePrice(BigDecimal.valueOf(1000));
        requestDTO.setTypeService(Service.TypeService.OTHER);
        requestDTO.setDate(new Date());

        responseDTO = new ServiceResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setServiceName("Массаж");
    }

    @Test
    void addServiceTest_success() throws Exception {
        doNothing().when(serviceService).addService(any(ServiceRequestDTO.class));

        mockMvc.perform(post("/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());

        verify(serviceService).addService(any(ServiceRequestDTO.class));
    }

    @Test
    void showAllServicesTest_success() throws Exception {
        List<ServiceResponseDTO> list = new ArrayList<>();
        list.add(responseDTO);
        when(serviceService.getAllServices()).thenReturn(list);

        mockMvc.perform(get("/services"))
                .andExpect(status().isOk());

        verify(serviceService).getAllServices();
    }

    @Test
    void deleteServiceTest_success() throws Exception {
        when(serviceService.getServiceById(1L)).thenReturn(responseDTO);
        doNothing().when(serviceService).deleteService(1L);

        mockMvc.perform(delete("/services/1"))
                .andExpect(status().isNoContent());

        verify(serviceService).deleteService(1L);
    }

    @Test
    void deleteServiceTest_fail() throws Exception {
        when(serviceService.getServiceById(999L)).thenReturn(null);

        mockMvc.perform(delete("/services/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateServiceTest_success() throws Exception {
        when(serviceService.getServiceById(1L)).thenReturn(responseDTO);
        doNothing().when(serviceService).updateService(eq(1L), any(ServiceRequestDTO.class));

        mockMvc.perform(put("/services/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());

        verify(serviceService).updateService(eq(1L), any(ServiceRequestDTO.class));
    }

    @Test
    void sortServicesTest_success() throws Exception {
        List<ServiceResponseDTO> list = new ArrayList<>();
        list.add(responseDTO);
        when(serviceService.sort("price")).thenReturn(list);

        mockMvc.perform(get("/services/sort/price"))
                .andExpect(status().isOk());

        verify(serviceService).sort("price");
    }

    @Test
    void getServiceByIdTest_success() throws Exception {
        when(serviceService.getServiceById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/services/1"))
                .andExpect(status().isOk());

        verify(serviceService).getServiceById(1L);
    }

}
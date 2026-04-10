package com.hotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.dto.BookingRequestDTO;
import com.hotel.dto.BookingResponseDTO;
import com.hotel.dto.ClientResponseDTO;
import com.hotel.dto.RoomDTO;
import com.hotel.exceptions.NotFoundException;
import com.hotel.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private ObjectMapper objectMapper;
    private BookingRequestDTO requestDTO;
    private BookingResponseDTO responseDTO;
    private RoomDTO roomDTO;
    private ClientResponseDTO clientDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

        requestDTO = new BookingRequestDTO();
        requestDTO.setClient(new ClientResponseDTO());
        requestDTO.setRoom(new RoomDTO());
        requestDTO.setCheckInDate(new Date());
        requestDTO.setCheckOutDate(new Date());

        responseDTO = new BookingResponseDTO();
        responseDTO.setId(1L);

        roomDTO = new RoomDTO();
        roomDTO.setRoomNumber(101);

        clientDTO = new ClientResponseDTO();
        clientDTO.setId(1L);
    }

    @Test
    void addBookingTest_success() throws Exception {
        doNothing().when(bookingService).addBooking(any(BookingRequestDTO.class));

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());

        verify(bookingService).addBooking(any(BookingRequestDTO.class));
    }

    @Test
    void showAllBookingsTest_success() throws Exception {
        List<BookingResponseDTO> list = new ArrayList<>();
        list.add(responseDTO);
        when(bookingService.getAllBookings()).thenReturn(list);

        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk());

        verify(bookingService).getAllBookings();
    }

    @Test
    void showAllBookingsTest_empty() throws Exception {
        when(bookingService.getAllBookings()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk());

        verify(bookingService).getAllBookings();
    }

    @Test
    void deleteBookingTest_success() throws Exception {
        when(bookingService.getBookingById(1L)).thenReturn(responseDTO);
        doNothing().when(bookingService).deleteBooking(1L);

        mockMvc.perform(delete("/bookings/delete/1"))
                .andExpect(status().isNoContent());

        verify(bookingService).deleteBooking(1L);
    }

    @Test
    void updateBookingTest_success() throws Exception {
        doNothing().when(bookingService).updateBooking(eq(1L), any(BookingRequestDTO.class));

        mockMvc.perform(put("/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());

        verify(bookingService).updateBooking(eq(1L), any(BookingRequestDTO.class));
    }

    @Test
    void showFreeRoomsByDateTest_success() throws Exception {
        List<RoomDTO> list = new ArrayList<>();
        list.add(roomDTO);
        when(bookingService.getFreeRoomsByDate(any(Date.class), any(Date.class))).thenReturn(list);

        mockMvc.perform(get("/bookings/showFree/2024-01-01/2024-01-10"))
                .andExpect(status().isOk());

        verify(bookingService).getFreeRoomsByDate(any(Date.class), any(Date.class));
    }

    @Test
    void showFreeRoomsByDateTest_empty() throws Exception {
        List<RoomDTO> emptyList = new ArrayList<>();
        when(bookingService.getFreeRoomsByDate(any(Date.class), any(Date.class))).thenReturn(emptyList);

        mockMvc.perform(get("/bookings/showFree/2024-01-01/2024-01-10"))
                .andExpect(status().isOk());

        verify(bookingService).getFreeRoomsByDate(any(Date.class), any(Date.class));
    }

    @Test
    void showFreeRoomsByDateTest_invalidDateFormat() throws Exception {
        mockMvc.perform(get("/bookings/showFree/01-01-2024/10-01-2024"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void showLastThreeBookingsTest_success() throws Exception {
        List<BookingResponseDTO> list = new ArrayList<>();
        list.add(responseDTO);
        when(bookingService.lastThreeBookingsByRooms(101)).thenReturn(list);

        mockMvc.perform(get("/bookings/showLastThree/101"))
                .andExpect(status().isOk());
    }

    @Test
    void sortBookingsTest_success() throws Exception {
        List<BookingResponseDTO> list = new ArrayList<>();
        list.add(responseDTO);
        when(bookingService.sort("checkInDate")).thenReturn(list);

        mockMvc.perform(get("/bookings/sort/checkInDate"))
                .andExpect(status().isOk());
    }

    @Test
    void getClientsStaysByRoomTest_success() throws Exception {
        List<ClientResponseDTO> list = new ArrayList<>();
        list.add(clientDTO);
        when(bookingService.getClientsStaysByRoom(101)).thenReturn(list);

        mockMvc.perform(get("/bookings/showClientStays/101"))
                .andExpect(status().isOk());
    }
}
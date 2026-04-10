package com.hotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.dto.RoomDTO;
import com.hotel.exceptions.NotFoundException;
import com.hotel.model.Room;
import com.hotel.service.RoomService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RoomControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    private ObjectMapper objectMapper;
    private RoomDTO roomDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).setValidator(null).build();

        roomDTO = new RoomDTO();
        roomDTO.setRoomNumber(101);
        roomDTO.setPrice(BigDecimal.valueOf(100));
        roomDTO.setPlace(2);
        roomDTO.setType(Room.RoomType.STANDART);
        roomDTO.setStars(Room.Star.FOUR);
    }

    @Test
    void addRoomTest_success() throws Exception {
        doNothing().when(roomService).addRoom(any(RoomDTO.class));

        mockMvc.perform(post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDTO)))
                .andExpect(status().isOk());

        verify(roomService).addRoom(any(RoomDTO.class));
    }

    @Test
    void showAllRoomsTest_success() throws Exception {
        List<RoomDTO> list = new ArrayList<>();
        list.add(roomDTO);
        when(roomService.getAllRooms()).thenReturn(list);

        mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk());

        verify(roomService).getAllRooms();
    }

    @Test
    void deleteRoomTest_success() throws Exception {
        doNothing().when(roomService).deleteRoom(101);

        mockMvc.perform(delete("/rooms/101"))
                .andExpect(status().isNoContent());

        verify(roomService).deleteRoom(101);
    }

    @Test
    void updateRoomTest_success() throws Exception {
        when(roomService.getRoomByRoomNumber(101)).thenReturn(roomDTO);
        doNothing().when(roomService).updateRoom(any(RoomDTO.class));

        mockMvc.perform(put("/rooms/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDTO)))
                .andExpect(status().isOk());

        verify(roomService).updateRoom(any(RoomDTO.class));
    }

    @Test
    void showRoomsByStatusTest_success() throws Exception {
        List<RoomDTO> list = new ArrayList<>();
        list.add(roomDTO);
        when(roomService.getRoomByStatus(Room.Status.FREE)).thenReturn(list);

        mockMvc.perform(get("/rooms/status/FREE"))
                .andExpect(status().isOk());

        verify(roomService).getRoomByStatus(Room.Status.FREE);
    }

    @Test
    void showFreeRoomsCountTest_success() throws Exception {
        when(roomService.countFreeRooms()).thenReturn(3);

        mockMvc.perform(get("/rooms/countfree"))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }

    @Test
    void sortRoomsTest_success() throws Exception {
        List<RoomDTO> list = new ArrayList<>();
        list.add(roomDTO);
        when(roomService.sort("price")).thenReturn(list);

        mockMvc.perform(get("/rooms/sort/price"))
                .andExpect(status().isOk());

        verify(roomService).sort("price");
    }

    @Test
    void findRoomByNumberTest_success() throws Exception {
        when(roomService.getRoomByRoomNumber(101)).thenReturn(roomDTO);

        mockMvc.perform(get("/rooms/101"))
                .andExpect(status().isOk());

        verify(roomService).getRoomByRoomNumber(101);
    }

    @Test
    void changeRoomStatusTest_success() throws Exception {
        doNothing().when(roomService).changeStatus(101, Room.Status.REPAIR);

        mockMvc.perform(patch("/rooms/101/REPAIR"))
                .andExpect(status().isOk());

        verify(roomService).changeStatus(101, Room.Status.REPAIR);
    }
}
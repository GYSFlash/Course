package com.hotel.service;

import com.hotel.dto.RoomDTO;
import com.hotel.exceptions.NoIllegalArgumentException;
import com.hotel.exceptions.NotFoundException;
import com.hotel.mapper.RoomMapper;
import com.hotel.model.Room;
import com.hotel.repository.BookingRepository;
import com.hotel.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomMapper roomMapper;

    @InjectMocks
    private RoomServiceImpl roomService;

    private RoomDTO roomDTO;
    private Room room;
    private List<RoomDTO> roomDTOList;

    @BeforeEach
    void setUp() {
        roomDTO = new RoomDTO();
        roomDTO.setRoomNumber(101);
        roomDTO.setPrice(BigDecimal.valueOf(100));
        roomDTO.setPlace(2);
        roomDTO.setType(Room.RoomType.STANDART);
        roomDTO.setStars(Room.Star.FOUR);

        room = new Room();
        room.setRoomNumber(101);
        room.setPrice(BigDecimal.valueOf(100));
        room.setPlace(2);
        room.setType(Room.RoomType.STANDART);
        room.setStars(Room.Star.FOUR);
        roomDTOList = new ArrayList<>();
        roomDTOList.add(roomDTO);
    }

    @Test
    void addRoomTest_success() {
        when(roomMapper.toRoom(roomDTO)).thenReturn(room);

        roomService.addRoom(roomDTO);

        verify(roomRepository).create(room);
    }

    @Test
    void addRoomTest_fail() {
        when(roomMapper.toRoom(roomDTO)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> roomService.addRoom(roomDTO));
    }

    @Test
    void deleteRoomTest_success() {
        when(bookingRepository.deleteByRoomNumber(101)).thenReturn(true);
        when(roomRepository.deleteById(101)).thenReturn(true);

        roomService.deleteRoom(101);

        verify(bookingRepository).deleteByRoomNumber(101);
        verify(roomRepository).deleteById(101);
    }

    @Test
    void deleteRoomTest_fail() {
        doThrow(new RuntimeException()).when(roomRepository).deleteById(101);

        assertThrows(RuntimeException.class, () -> roomService.deleteRoom(101));
    }

    @Test
    void updateRoomTest_success() {
        when(roomMapper.toRoom(roomDTO)).thenReturn(room);
        when(roomRepository.findById(101)).thenReturn(Optional.of(room));
        when(roomService.getRoomByRoomNumber(101)).thenReturn(roomDTO);
        roomService.updateRoom(roomDTO);

        verify(roomRepository).update(room);
    }

    @Test
    void updateRoomTest_fail() {
        when(roomRepository.findById(101)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roomService.updateRoom(roomDTO));
    }

    @Test
    void getAllRoomsTest_success() {
        when(roomRepository.findAll()).thenReturn(List.of(room));
        when(roomMapper.toRoomDTOList(List.of(room))).thenReturn(List.of(roomDTO));

        List<RoomDTO> result = roomService.getAllRooms();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllRoomsTest_fail() {
        when(roomRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> roomService.getAllRooms());
    }

    @Test
    void getRoomByRoomNumberTest_success() {
        when(roomRepository.findById(101)).thenReturn(Optional.of(room));
        when(roomMapper.toRoomDTO(room)).thenReturn(roomDTO);

        RoomDTO result = roomService.getRoomByRoomNumber(101);

        assertNotNull(result);
        assertEquals(101, result.getRoomNumber());
    }

    @Test
    void getRoomByRoomNumberTest_fail() {
        when(roomRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roomService.getRoomByRoomNumber(999));
    }

    @Test
    void getRoomByStatusTest_success() {
        when(roomRepository.findByStatus(Room.Status.FREE)).thenReturn(List.of(room));
        when(roomMapper.toRoomDTOList(List.of(room))).thenReturn(List.of(roomDTO));

        List<RoomDTO> result = roomService.getRoomByStatus(Room.Status.FREE);

        assertNotNull(result);
    }

    @Test
    void getRoomByStatusTest_fail() {
        when(roomRepository.findByStatus(Room.Status.FREE)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> roomService.getRoomByStatus(Room.Status.FREE));
    }

    @Test
    void countFreeRoomsTest_success() {
        when(roomRepository.countFreeRoom()).thenReturn(5);

        int result = roomService.countFreeRooms();

        assertEquals(5, result);
    }

    @Test
    void countFreeRoomsTest_fail() {
        when(roomRepository.countFreeRoom()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> roomService.countFreeRooms());
    }

    @Test
    void sortTest_success() {
        when(roomRepository.findAll()).thenReturn(List.of(room));
        when(roomMapper.toRoomDTOList(List.of(room))).thenReturn(roomDTOList);

        List<RoomDTO> result = roomService.sort("price");

        assertNotNull(result);
        verify(roomRepository).findAll();
    }

    @Test
    void sortTest_fail() {
        when(roomRepository.findAll()).thenReturn(List.of(room));
        when(roomMapper.toRoomDTOList(List.of(room))).thenReturn(List.of(roomDTO));

        assertThrows(NoIllegalArgumentException.class, () -> roomService.sort("invalid"));
    }

    @Test
    void sortTest_empty() {
        when(roomRepository.findAll()).thenReturn(List.of());
        when(roomMapper.toRoomDTOList(List.of())).thenReturn(List.of());

        List<RoomDTO> result = roomService.sort("price");

        assertNull(result);
    }
}
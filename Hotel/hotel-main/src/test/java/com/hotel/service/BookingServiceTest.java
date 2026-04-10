package com.hotel.service;
import com.hotel.dto.*;
import com.hotel.exceptions.NoIllegalArgumentException;
import com.hotel.exceptions.NotFoundException;
import com.hotel.mapper.BookingMapper;
import com.hotel.model.Booking;
import com.hotel.model.Client;
import com.hotel.model.Room;
import com.hotel.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Spy
    @InjectMocks
    private BookingServiceImpl bookingService;
    @Mock
    private RoomService roomService;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private BookingMapper mapper;

    @Test
    void deleteBookingTest_success() {
        bookingService.deleteBooking(1L);
        verify(bookingRepository).deleteById(1L);
    }
    @Test
    void addBookingTest_success() {
        BookingRequestDTO dto = mock(BookingRequestDTO.class);
        Booking booking = new Booking();
        when(dto.getClient()).thenReturn(new ClientResponseDTO());
        when(dto.getRoom()).thenReturn(new RoomDTO());
        when(dto.calculateTotalPrice()).thenReturn(new BigDecimal(100));
        when(mapper.toBooking(dto)).thenReturn(booking);
        bookingService.addBooking(dto);
        verify(bookingRepository).create(booking);
    }
    @Test
    void addBookingTest_fail() {
        BookingRequestDTO dto = new BookingRequestDTO();
        assertThrows(NoIllegalArgumentException.class,
                () -> bookingService.addBooking(dto));
    }
    @Test
    void getAllBookingsTest_success() {
        List<Booking> bookings = new ArrayList<>();
        List<BookingResponseDTO> dtos = new ArrayList<>();
        when(bookingRepository.findAll()).thenReturn(bookings);
        when(mapper.toBookingDTOList(bookings)).thenReturn(dtos);

        List<BookingResponseDTO> result = bookingService.getAllBookings();
        assertNotNull(result);
        verify(bookingRepository).findAll();
    }
    @Test
    void getAllBookingsTest_fail() {
        when(bookingRepository.findAll()).thenReturn(Collections.emptyList());
        when(mapper.toBookingDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());
        List<BookingResponseDTO> result = bookingService.getAllBookings();
        assertTrue(result.isEmpty());
    }
    @Test
    void updateBookingTest_success() {
        BookingRequestDTO dto = mock(BookingRequestDTO.class);
        Booking booking = new Booking();
        doReturn(new BookingResponseDTO()).when(bookingService).getBookingById(1L);
        when(mapper.toBooking(dto)).thenReturn(booking);
        bookingService.updateBooking(1L,dto);
        verify(bookingRepository).update(booking);
    }
    @Test
    void updateBookingTest_fail() {
        doThrow(new NotFoundException("not found"))
                .when(bookingService).getBookingById(1L);
        assertThrows(NotFoundException.class,
                () -> bookingService.updateBooking(1L, new BookingRequestDTO()));
    }
    @Test
    void getFreeRoomsByDateTest_success() {
        Date in = new Date();
        Date out = new Date(System.currentTimeMillis() + 86400000);
        RoomDTO roomDTO = mock(RoomDTO.class);
        RoomDTO roomDTO1 = mock(RoomDTO.class);
        when(roomDTO.getStatus()).thenReturn(Room.Status.FREE);
        when(roomDTO1.getStatus()).thenReturn(Room.Status.REPAIR);
        when(roomService.getAllRooms()).thenReturn(List.of(roomDTO, roomDTO1));
        doReturn(new ArrayList<>()).when(bookingService).getAllBookings();
        List<RoomDTO> result = bookingService.getFreeRoomsByDate(in, out);
        assertEquals(result.size(), 1);
    }
    @Test
    void getFreeRoomsByDateTest_fail() {
        Date in = new Date();
        Date out = new Date(System.currentTimeMillis() + 86400000);
        RoomDTO roomDTO = mock(RoomDTO.class);
        RoomDTO roomDTO1 = mock(RoomDTO.class);
        when(roomDTO.getStatus()).thenReturn(Room.Status.REPAIR);
        when(roomDTO1.getStatus()).thenReturn(Room.Status.REPAIR);
        when(roomService.getAllRooms()).thenReturn(List.of(roomDTO, roomDTO1));
        doReturn(new ArrayList<>()).when(bookingService).getAllBookings();
        List<RoomDTO> result = bookingService.getFreeRoomsByDate(in, out);
        assertTrue(result.isEmpty());
    }
    @Test
    void lastThreeBookingsByRooms_success() {
        List<Booking> bookings = List.of(new Booking(),new Booking(),new Booking());
        List<BookingResponseDTO> dtoList = List.of(new BookingResponseDTO(),new BookingResponseDTO(),new BookingResponseDTO());

        when(bookingRepository.threeBookingByRoom(101)).thenReturn(bookings);
        when(mapper.toBookingDTOList(bookings)).thenReturn(dtoList);

        List<BookingResponseDTO> result =
                bookingService.lastThreeBookingsByRooms(101);

        assertEquals(result.size(), 3);
    }
    @Test
    void lastThreeBookingsByRooms_fail() {
        List<Booking> bookings = List.of();
        List<BookingResponseDTO> dtoList = List.of();

        when(bookingRepository.threeBookingByRoom(101)).thenReturn(bookings);
        when(mapper.toBookingDTOList(bookings)).thenReturn(dtoList);

        List<BookingResponseDTO> result =
                bookingService.lastThreeBookingsByRooms(101);

        assertTrue(result.isEmpty());
    }
    @Test
    void getBookingByIdTest_success() {
        Booking booking = new Booking();
        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(mapper.toBookingDTO(booking)).thenReturn(bookingResponseDTO);
        BookingResponseDTO result = bookingService.getBookingById(1L);
        assertEquals(result, bookingResponseDTO);
    }
    @Test
    void getBookingByIdTest_fail() {
        when(bookingRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> bookingService.getBookingById(1L));
    }
    @Test
    void sortTest_success(){
        BookingResponseDTO dto1 = mock(BookingResponseDTO.class);
        BookingResponseDTO dto2 = mock(BookingResponseDTO.class);

        Date earlier = new Date(1000);
        Date later = new Date(2000);

        when(dto1.getCheckInDate()).thenReturn(later);
        when(dto2.getCheckInDate()).thenReturn(earlier);

        List<BookingResponseDTO> list = new ArrayList<>(List.of(dto1, dto2));

        doReturn(list).when(bookingService).getAllBookings();

        List<BookingResponseDTO> result =
                bookingService.sort("checkInDate");

        assertEquals(earlier, result.get(0).getCheckInDate());
        assertEquals(later, result.get(1).getCheckInDate());
    }
    @Test
    void sortTest_fail(){
        BookingResponseDTO bookingResponseDTO = mock(BookingResponseDTO.class);
        List<BookingResponseDTO> list = List.of(bookingResponseDTO);
        doReturn(list).when(bookingService).getAllBookings();
        assertThrows(NoIllegalArgumentException.class, () -> bookingService.sort("sajdhjkas"));
    }
    @Test
    void sortTest_empty(){
        doReturn(new ArrayList<>()).when(bookingService).getAllBookings();
        assertThrows(NotFoundException.class, () -> bookingService.sort("checkInDate"));
    }
    @Test
    void getClientStaysByRoomsTest_success(){
        BookingResponseDTO booking = mock(BookingResponseDTO.class);
        RoomDTO room = mock(RoomDTO.class);
        ClientResponseDTO client = mock(ClientResponseDTO.class);

        when(room.getRoomNumber()).thenReturn(1);
        when(booking.getRoom()).thenReturn(room);
        when(booking.getClient()).thenReturn(client);
        List<BookingResponseDTO> bookings = new ArrayList<>(List.of(booking));

        when(bookingRepository.findAll()).thenReturn(List.of(new Booking()));
        when(mapper.toBookingDTOList(anyList())).thenReturn(bookings);

        List<ClientResponseDTO> result = bookingService.getClientsStaysByRoom(1);

        assertNotNull(result);
        assertEquals(1, result.size());
    }
    @Test
    void getClientsStaysByRoom_fail() {
        doReturn(new ArrayList<>())
                .when(bookingService).getAllBookings();

        List<ClientResponseDTO> result =
                bookingService.getClientsStaysByRoom(1);

        assertNull(result);
    }

}

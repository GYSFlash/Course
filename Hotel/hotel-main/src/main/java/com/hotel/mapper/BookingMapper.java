package com.hotel.mapper;

import com.hotel.dto.BookingRequestDTO;
import com.hotel.dto.BookingResponseDTO;
import com.hotel.model.Booking;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingResponseDTO toBookingDTO(Booking booking);
    Booking toBooking(BookingRequestDTO bookingRequestDTO);
    List<BookingResponseDTO> toBookingDTOList(List<Booking> bookings);
}

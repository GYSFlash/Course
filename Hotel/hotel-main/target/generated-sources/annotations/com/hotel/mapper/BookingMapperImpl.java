package com.hotel.mapper;

import com.hotel.dto.BookingRequestDTO;
import com.hotel.dto.BookingResponseDTO;
import com.hotel.dto.ClientResponseDTO;
import com.hotel.dto.RoomDTO;
import com.hotel.model.Booking;
import com.hotel.model.Client;
import com.hotel.model.Room;
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
public class BookingMapperImpl implements BookingMapper {

    @Override
    public BookingResponseDTO toBookingDTO(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();

        bookingResponseDTO.setCheckInDate( booking.getCheckInDate() );
        bookingResponseDTO.setCheckOutDate( booking.getCheckOutDate() );
        bookingResponseDTO.setClient( clientToClientResponseDTO( booking.getClient() ) );
        bookingResponseDTO.setId( booking.getId() );
        bookingResponseDTO.setRoom( roomToRoomDTO( booking.getRoom() ) );
        bookingResponseDTO.setTotalPrice( booking.getTotalPrice() );

        return bookingResponseDTO;
    }

    @Override
    public Booking toBooking(BookingRequestDTO bookingRequestDTO) {
        if ( bookingRequestDTO == null ) {
            return null;
        }

        Booking booking = new Booking();

        booking.setCheckInDate( bookingRequestDTO.getCheckInDate() );
        booking.setCheckOutDate( bookingRequestDTO.getCheckOutDate() );
        booking.setClient( clientResponseDTOToClient( bookingRequestDTO.getClient() ) );
        booking.setRoom( roomDTOToRoom( bookingRequestDTO.getRoom() ) );
        booking.setTotalPrice( bookingRequestDTO.getTotalPrice() );

        return booking;
    }

    @Override
    public List<BookingResponseDTO> toBookingDTOList(List<Booking> bookings) {
        if ( bookings == null ) {
            return null;
        }

        List<BookingResponseDTO> list = new ArrayList<BookingResponseDTO>( bookings.size() );
        for ( Booking booking : bookings ) {
            list.add( toBookingDTO( booking ) );
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

    protected RoomDTO roomToRoomDTO(Room room) {
        if ( room == null ) {
            return null;
        }

        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setPlace( room.getPlace() );
        roomDTO.setPrice( room.getPrice() );
        roomDTO.setRoomNumber( room.getRoomNumber() );
        roomDTO.setStars( room.getStars() );
        roomDTO.setStatus( room.getStatus() );
        roomDTO.setType( room.getType() );

        return roomDTO;
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

    protected Room roomDTOToRoom(RoomDTO roomDTO) {
        if ( roomDTO == null ) {
            return null;
        }

        Room room = new Room();

        room.setPrice( roomDTO.getPrice() );
        room.setPlace( roomDTO.getPlace() );
        room.setRoomNumber( roomDTO.getRoomNumber() );
        room.setStatus( roomDTO.getStatus() );
        room.setStars( roomDTO.getStars() );
        room.setType( roomDTO.getType() );

        return room;
    }
}

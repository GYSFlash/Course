package com.hotel.mapper;

import com.hotel.dto.RoomDTO;
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
public class RoomMapperImpl implements RoomMapper {

    @Override
    public RoomDTO toRoomDTO(Room room) {
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

    @Override
    public Room toRoom(RoomDTO roomDTO) {
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

    @Override
    public List<RoomDTO> toRoomDTOList(List<Room> rooms) {
        if ( rooms == null ) {
            return null;
        }

        List<RoomDTO> list = new ArrayList<RoomDTO>( rooms.size() );
        for ( Room room : rooms ) {
            list.add( toRoomDTO( room ) );
        }

        return list;
    }
}

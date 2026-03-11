package com.hotel.mapper;

import com.hotel.dto.RoomDTO;
import com.hotel.model.Room;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomDTO toRoomDTO(Room room);
    Room toRoom(RoomDTO roomDTO);
    List<RoomDTO> toRoomDTOList(List<Room> rooms);
}

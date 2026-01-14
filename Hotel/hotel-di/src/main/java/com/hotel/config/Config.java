package com.hotel.config;


import com.hotel.annotations.ConfigProperty;
import com.hotel.annotations.PropertyType;

public class Config {
    @ConfigProperty(
            propertyName = "room.status.change.enable",
            propertyType = PropertyType.BOOLEAN)
    private boolean roomStatusChangeEnable;

    @ConfigProperty(
            propertyName = "booking.history.record.limit",
            propertyType = PropertyType.INT)
    private int bookingHistoryRecordLimit;

    @ConfigProperty(
            propertyName = "filetype.data.used",
            propertyType = PropertyType.STRING)
    private static String filetypeDataUsed;
    @ConfigProperty(
            propertyName = "view.type",
            propertyType = PropertyType.STRING)
    private String viewType;

    public boolean isRoomStatusChangeEnable() {
        return roomStatusChangeEnable;
    }
    public int getBookingHistoryRecordLimit() {
        return bookingHistoryRecordLimit;
    }
    public static String getFiletypeDataUsed() {
        return filetypeDataUsed;
    }
    public String getViewType() {
        return viewType;
    }
}

package config;


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
    private String filetypeDataUsed;

    public boolean isRoomStatusChangeEnable() {
        return roomStatusChangeEnable;
    }
    public int getBookingHistoryRecordLimit() {
        return bookingHistoryRecordLimit;
    }
    public String getFiletypeDataUsed() {
        return filetypeDataUsed;
    }
}

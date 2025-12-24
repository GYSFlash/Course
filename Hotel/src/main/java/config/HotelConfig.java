package config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HotelConfig {
    public static final Properties properties = new Properties();
    private static final String CONFIG_PATH = "Hotel/src/main/resources/config.properties";

    static {
        try (FileReader reader = new FileReader(CONFIG_PATH)) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить файл конфигурации: " + CONFIG_PATH, e);
        }
    }
    public static boolean getRoomStatusChange() {
        return Boolean.parseBoolean(properties.getProperty("room.status.change.enable"));
    }
    public static int getClientsByRoomLimit() {
        return Integer.parseInt(properties.getProperty("booking.history.record.limit"));
    }
    public static String getNameFileType(){
        return properties.getProperty("filetype.data.used");
    }
}

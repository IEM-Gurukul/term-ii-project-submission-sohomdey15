import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Error loading config.properties: " + e.getMessage());
        }
    }

    public static int getTotalSlots() {
        return Integer.parseInt(properties.getProperty("totalSlots", "10"));
    }

    public static double getHourlyRate() {
        return Double.parseDouble(properties.getProperty("hourlyRate", "5.0"));
    }

    public static int getMaxHours() {
        return Integer.parseInt(properties.getProperty("maxHours", "24"));
    }
}
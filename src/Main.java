import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        GlobalWeatherManager manager = new GlobalWeatherManager(new File("city_temperature.csv"));
        System.out.println("Reading counts = " + manager.getReadingCount());
        System.out.println();
        System.out.println("TemperatureLinearRegressionSlope "+ manager.getTemperatureLinearRegressionSlope(manager.getReadings(5,1092,10,5)));
        System.out.println();
        System.out.println("City Stats " + manager.getCityListStats("US", "Washington", "Seattle"));
        System.out.println(Arrays.toString(manager.getReadings(2683539,3)));
    }
}



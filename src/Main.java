import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        GlobalWeatherManager manager = new GlobalWeatherManager(new File("city_temperature.csv"));

       System.out.println("Reading counts = " + manager.getReadingCount());
//       System.out.println();
//       System.out.println("Get reading at specific index = " + manager.getReading(2885060));
//       System.out.println();
//       System.out.println("Get readings: " + Arrays.toString(manager.getReadings(0,2000000,5,16)));
//       System.out.println();
//       System.out.println("TemperatureLinearRegressionSlope "+ manager.getTemperatureLinearRegressionSlope(manager.getReadings(5,1092,10,5)));
//       System.out.println();
//        Iterator<WeatherReading> iterate = manager.readWeather.iterator();
//        System.out.println("iterate.next() = " + iterate.next());
//        System.out.println();
//        System.out.println("City Stats " + manager.getCityListStats("US", "Washington", "Seattle"));
    }
}



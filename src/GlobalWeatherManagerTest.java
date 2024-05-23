import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalWeatherManagerTest {
    @Test
    public void constuctTest() throws FileNotFoundException {
        GlobalWeatherManager manager = new GlobalWeatherManager(new File("city_temperature.csv"));
        assertThrows(FileNotFoundException.class,
                () -> new GlobalWeatherManager(new File("hi.csv")));
        assertEquals(2885064,manager.getReadingCount());
    }

    @Test
    public void getReadingCount() throws FileNotFoundException {
        GlobalWeatherManager testManager = new GlobalWeatherManager(new File("city_temperature.csv"));
        assertEquals(2885064,testManager.getReadingCount());
    }

    @Test
    public void getReading() throws FileNotFoundException {
        GlobalWeatherManager testManager = new GlobalWeatherManager(new File("city_temperature.csv"));
        WeatherReading readWeather = new WeatherReading("Africa","Algeria","","Algiers",1,3,1995,48.8);
        assertEquals(readWeather,testManager.getReading(2));
        assertThrows(IllegalArgumentException.class,
                () -> testManager.getReading(-6));
    }

    @Test
    public void getReadings() throws FileNotFoundException {
        GlobalWeatherManager testManager = new GlobalWeatherManager(new File("city_temperature.csv"));
        WeatherReading[] readWeatherArray = {testManager.getReading(0),testManager.getReading(1),testManager.getReading(2),testManager.getReading(3)};
        assertEquals(Arrays.toString(readWeatherArray),Arrays.toString(testManager.getReadings(0,4)));
        assertThrows(IllegalArgumentException.class,
                () -> testManager.getReadings(-1,1));
        assertThrows(IllegalArgumentException.class,
                () -> testManager.getReadings(0,0));
    }

    @Test
    public void testGetReadings() throws FileNotFoundException {
        GlobalWeatherManager testManager = new GlobalWeatherManager(new File("city_temperature.csv"));
        WeatherReading[] weatherArry = {testManager.getReading(0)};
        assertEquals(Arrays.toString(weatherArry),Arrays.toString(testManager.getReadings(0,364,1,1)));
        assertThrows(IllegalArgumentException.class,
                () -> testManager.getReadings(-1,4,1,1));
        assertThrows(IllegalArgumentException.class,
                () -> testManager.getReadings(0,2885064,1,1));
        assertThrows(IllegalArgumentException.class,
                () -> testManager.getReadings(0,5,13,1));
        assertThrows(IllegalArgumentException.class,
                () -> testManager.getReadings(0,5,1,33));
    }

    @Test
    public void getCityListStats() {
    }

    @Test
    public void iterator() throws FileNotFoundException {
        GlobalWeatherManager iterateTest = new GlobalWeatherManager(new File("city_temperature.csv"));
        Iterator<WeatherReading> iterate = iterateTest.iterator();
        WeatherReading firstReading = new WeatherReading("Africa","Algeria","","Algiers",1,1,1995,64.2);
        assertEquals(firstReading,iterate.next());
    }

    @Test
    public void getTemperatureLinearRegressionSlope() throws FileNotFoundException {
        GlobalWeatherManager testManager = new GlobalWeatherManager(new File("city_temperature.csv"));
        assertEquals(-3.25,testManager.getTemperatureLinearRegressionSlope(testManager.getReadings(0,1029,1,1)));
        assertThrows(IllegalArgumentException.class,
                () ->testManager.getTemperatureLinearRegressionSlope(testManager.getReadings(0,5,1,1)));
    }

    @Test
    void calcLinearRegressionSlope() throws FileNotFoundException {
        GlobalWeatherManager testManager = new GlobalWeatherManager(new File("city_temperature.csv"));
        Integer[] x = {1,2,3};
        Double[] y = {1.1,2.3,5.4};
        assertEquals(2.15,testManager.calcLinearRegressionSlope(x,y));
        Integer[] xless = {1};
        assertThrows(IllegalArgumentException.class,
                () -> testManager.calcLinearRegressionSlope(xless,y));
        Double[] yless = {1.1,1.2};
        assertThrows(IllegalArgumentException.class,
                () -> testManager.calcLinearRegressionSlope(x,yless));
    }
}
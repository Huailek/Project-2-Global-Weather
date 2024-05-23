import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * A class that handles all weatherReading.
 * @author  Niang Hual
 * @version 2023-4-19
 */
public class GlobalWeatherManager implements GlobalWeatherManagerInterface{
    /** Arraylist of WeatherReading object. */
    ArrayList<WeatherReading> readWeather = new ArrayList<>();

    /**
     * Constructor for GlobalWeatherManager
     * @param file  the file to read;
     */
    public GlobalWeatherManager(File file) throws FileNotFoundException {
        Scanner scanFile = new Scanner(file);
        scanFile.nextLine();
        while(scanFile.hasNextLine()) {
            String line = scanFile.nextLine();
            String[] oneLine = line.split(",");
            readWeather.add(new WeatherReading(oneLine[0],oneLine[1],oneLine[2],oneLine[3],Integer.parseInt(oneLine[4]), Integer.parseInt(oneLine[5]), Integer.parseInt(oneLine[6]), Double.parseDouble(oneLine[7])));
        }
        scanFile.close();
    }

    /**
     * Retrieves a count of readings
     * @return count of readings
     */
    @Override
    public int getReadingCount() {
        return readWeather.size();
    }

    /**
     * Retrieves the weather reading at the specified index.
     * @param index the index for the desired reading; must be a valid element index.
     * @return the reading at the specified index.
     */
    @Override
    public WeatherReading getReading(int index) {
        if (index < 0 || index >= readWeather.size()) {
            throw new IllegalArgumentException("must be valid index");
        }
        return readWeather.get(index);
    }

    /**
     * Retrieves a set of weather readings.
     *
     * @param index the index of the first reading; must be a valid index.
     * @param count the count of readings to potentially include.  Must be at least 1.  Must imply a valid range;
     *              index + count must be less than the total reading count.
     * @return an array of readings.
     */
    @Override
    public WeatherReading[] getReadings(int index, int count) {
        if (index < 0 || index >= readWeather.size()) {
            throw new IllegalArgumentException("must be valid index and count");
        }
        if (count < 1 ||(index+count) >= readWeather.size() ){
            throw new IllegalArgumentException("must be valid count");
        }
        WeatherReading[] tempReading = new WeatherReading[count];
        for (int readingIdx = 0; readingIdx < tempReading.length; readingIdx++) {
            tempReading[readingIdx] = readWeather.get(index + readingIdx);
        }
        return tempReading;
    }

    /**
     * Retrieves a set of weather readings.
     *
     * @param index the index of the first reading.
     * @param count the count of readings to check for potential inclusion.  Must be at least 1.
     *              Must imply a valid range; index + count must be less than the total reading count.
     * @param month the month to filter; must be a valid month (1 to 12).
     * @param day   the day to filter; must be a valid day (1 to 31).
     * @return an array of readings matching the specified criteria.  Length will usually be smaller than
     * the count specified as a parameter, as each year will only have one matching day.
     */
    @Override
    public WeatherReading[] getReadings(int index, int count, int month, int day) {
        if (index < 0 || index >= readWeather.size()) {
            throw new IllegalArgumentException("Index must be greater then 0");
        }
        if (count < 1 || (index+count) >= readWeather.size()) {
            throw new IllegalArgumentException("count must be greater then 0");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("month must be valid");
        }
        if(day < 1 || day > 31) {
            throw new IllegalArgumentException("days must be valid");
        }

        ArrayList<WeatherReading> tempArray = new ArrayList<>();

        for (int readingIdx = 0; readingIdx < count; readingIdx++) {
            WeatherReading temp = readWeather.get(index + readingIdx);
            if (temp.month() == month && temp.day() == day) {
                tempArray.add(temp);
            }
        }

        WeatherReading[] weatherArray = new WeatherReading[tempArray.size()];

        for (int i = 0; i < tempArray.size(); i++){
            weatherArray[i] = tempArray.get(i);
        }
        return weatherArray;
    }

    /**
     * Retrieves key list statistics for the specified country/state/city.
     * Student note:  okay to use an additional ArrayList in this method.
     *
     * @param country the country of interest; must not be null or blank.
     * @param state   the state of interest; must not be null.
     * @param city    the city of interest; must not be null or blank.
     * @return the list stats for the specified city, or null if not found.
     */
    @Override
    public CityListStats getCityListStats(String country, String state, String city) {
        if (country == null || country.isEmpty()) {
            throw new IllegalArgumentException("must not be empty or null");
        }
        if (state == null) {
            throw new IllegalArgumentException("must not be or null");
        }
        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("must not be empty or null");
        }
        WeatherReading temp = new WeatherReading("",country,state,city,1,1,1,0.00);
        Collections.sort(readWeather);
        int min = 0;
        int max = readWeather.size() - 1;
        int firstIndex = -1;
        while (min <= max) {
            int mid = (max + min) / 2;
            int compare = readWeather.get(mid).compareString(temp);
            if (compare == 0) {
                firstIndex = mid;
                max = mid - 1;
                //return mid;     // found it!
            } else if (compare < 0) {
                min = mid + 1;  // too small
            } else {   // compare > 0
                max = mid - 1;  // too large
            }
        }
        int count = 0;
        int index = firstIndex;
        while(readWeather.get(firstIndex).city().equalsIgnoreCase(readWeather.get(index).city())) {
            count++;
            index++;
        }
        ArrayList<Integer> yearsArray = new ArrayList<>();
        index = firstIndex;
        int currentYear = readWeather.get(firstIndex).year();
        yearsArray.add(currentYear);
        while(readWeather.get(firstIndex).city().equalsIgnoreCase(readWeather.get(index).city())) {
            if (currentYear != readWeather.get(index).year()) {
                yearsArray.add(readWeather.get(index).year());
                currentYear = readWeather.get(index).year();
            }
            index++;
        }

        int[] intYearsArray = new int[yearsArray.size()];

        for (int i = 0; i <yearsArray.size(); i++) {
            intYearsArray[i] = yearsArray.get(i);
        }
        return new CityListStats(firstIndex,count, intYearsArray);   // not found
    }

    /*
     * Retrieves an iterator over all weather readings.
     * @return strongly typed iterator for. */
    @Override
    public Iterator<WeatherReading> iterator() {
        return readWeather.iterator();
    }

    /**
     * Does a linear regression analysis on the data, using x = year and y = temperature.
     * Calculates the slope of a best-fit line using the Least Squares method.   For more information
     * on that method, see <a href="https://www.youtube.com/watch?v=P8hT5nDai6A">...</a>
     * Student note:  okay to use two additional ArrayLists in this method.
     *
     * @param readings array of readings to analyze.  Should typically be readings for a single day over
     *                 a number of years; larger data sets will likely yield better results.  Ignores
     *                 temperature data of -99.0, a default value indicating no temperature data was present.
     *                 Must not be null and must contain at least two readings.
     * @return slope of best-fit line; positive slope indicates increasing temperatures.
     */
    @Override
    public double getTemperatureLinearRegressionSlope(WeatherReading[] readings) {
        if (readings.length < 2 ) {
            throw new IllegalArgumentException("Array can't be null or less then 2");
        }
        int day = 0;
        ArrayList<Integer> dayArray = new ArrayList<>();
        ArrayList<Double> tempArray = new ArrayList<>();
        for (WeatherReading reading : readings) {
            if (reading != null && reading.avgTemperature() != -99) {
                day += 1;
                dayArray.add(day);
                tempArray.add(reading.avgTemperature());
            }
        }
        Integer[] tempDay = new Integer[dayArray.size()];
        for (int index = 0; index < tempDay.length; index++) {
            tempDay[index] = dayArray.get(index);
        }
        Double[] tempTemperature = new Double[tempArray.size()];
        for (int index = 0; index < tempTemperature.length; index++) {
            tempTemperature[index] = tempArray.get(index);
        }
        return calcLinearRegressionSlope(tempDay, tempTemperature);
    }

    /**
     * Calculates the slope of the best-fit line calculated using the Least Squares method.  For more information
     * on that method, see <a href="https://www.youtube.com/watch?v=P8hT5nDai6A">...</a>
     *
     * @param x an array of x values; must not be null and must contain at least two elements.
     * @param y an array of y values; must be the same length as the x array and must not be null.
     * @return the slope of the best-fit line
     */
    @Override
    public double calcLinearRegressionSlope(Integer[] x, Double[] y) {
        if ( x.length < 2) {
            throw new IllegalArgumentException("The int array can't be null or length less than 2");
        }
        if (y.length != x.length) {
            throw new IllegalArgumentException("Required both array be same length. Double array can't be null ");
        }
        int sumX = 0;
        double sumY = 0.0;
        int sumXSquared = 0;
        double sumXY = 0.0;
        for (int index = 0; index < x.length; index++) {
            sumX += x[index];
            sumY += y[index];
            sumXSquared += (int) Math.pow(x[index],2);
            sumXY += (x[index] * y[index]);
        }
        return  ((x.length)*sumXY - (sumX*sumY))/((x.length)*sumXSquared - Math.pow(sumX,2));
    }
}

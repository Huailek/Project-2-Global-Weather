import java.util.Arrays;

public record CityListStats(int startingIndex, int count, int[] years) {

//    CityListStats gives the client a package of useful data for them to explore a particular city.
//    It tells them the first index at which the city appears, how many entries there are in total for that city,
//    and a list of (unique) years for which data appears. They can then use other methods to get that data, loop through it, etc.


    public CityListStats {
        if (startingIndex < 0) {
            throw new IllegalArgumentException("can't be negative");
        }
        if (count < 0) {
            throw new IllegalArgumentException("can't be negative");
        }
        if (years == null){
            throw new IllegalArgumentException("can't be null");
        }

    }

    @Override
    public String toString(){
        return "startIndex: " + startingIndex + ", count: " + count + ", years: " + Arrays.toString(years);
    }
}

public record WeatherReading(String region, String country, String state, String city, int month, int day, int year, double avgTemperature) implements Comparable<WeatherReading> {
    public WeatherReading {
        if (region == null) {
            throw new IllegalArgumentException("region can't be empty or null");
        }
        if (country == null) {
            throw new IllegalArgumentException("country can't be null or empty");
        }
        if (state == null) {
            throw new IllegalArgumentException("state cannot be null or empty");
        }
        if (city == null) {
            throw new IllegalArgumentException("city cannot be null or empty");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("month can't be less then or equal to 0");
        }
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("day can't be less then or equal to 0");
        }
    }

    @Override
    public int compareTo(WeatherReading other) {
        int result = country.compareTo(other.country);
        if (result != 0) {
            return result;
        }
        result = state.compareTo(other.state);
        if (result != 0) {
            return result;
        }
        result = city.compareTo(other.city);
        if (result != 0) {
            return result;
        }
        result = Integer.compare(year, other.year);
        if (result != 0) {
            return result;
        }
        result = Integer.compare(month, other.month);
        if (result != 0) {
            return result;
        }
        return Integer.compare(day, other.day);
    }

    public int compareString(WeatherReading o) {
        int result = country.compareTo(o.country);
        if (result != 0) {
            return result;
        }
        result = state.compareTo(o.state);
        if (result != 0) {
            return result;
        }
        return result = city.compareTo(o.city);
    }

    public boolean equals(WeatherReading other) {
        if (other == null || !(other instanceof WeatherReading)) {
            return false;
        } else if (other == this) {
            return true;
        } else {
            WeatherReading otherWeatherReading = (WeatherReading) other;
            return this.compareTo(otherWeatherReading) == 0;
        }
    }
}

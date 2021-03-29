import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class WeatherForecast {
    private static ObjectMapper mapper = initObjectMapper();

    public static void main(String[] args) {

        System.out.println(getWeather("Moscow"));
    }

    private static ObjectMapper initObjectMapper(){
        ObjectMapper mapper = new  ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public static String getWeather(String city) {
        try {
            //            URL url = WeatherForecast.class.getResource("/json.json");
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid=df9391cda04901467aa1dae56a996f72");
            JsonNode node = mapper.readTree(url);
            OpenWeatherData open = mapper.readValue(url, OpenWeatherData.class);

            return open.toString();

//            return "Температура в " + city + " " + main.getDouble("temp");
        } catch (IOException e) {
            return "город " + city + " не найден";
//            return e.getMessage();
        }
    }
}

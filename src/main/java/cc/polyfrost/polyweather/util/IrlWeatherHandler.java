package cc.polyfrost.polyweather.util;

import cc.polyfrost.oneconfig.utils.Multithreading;
import cc.polyfrost.oneconfig.utils.NetworkUtils;
import cc.polyfrost.polyweather.config.WeatherConfig;
import com.google.gson.JsonObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;

public class IrlWeatherHandler {
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final HashMap<Instant, WMOCode> data = new HashMap<>();
    private static boolean fetching = false;

    public static void fetchData() {
        if (!WeatherConfig.irlWeather || fetching) return;
        fetching = true;
        Multithreading.runAsync(() -> {
            JsonObject location = NetworkUtils.getJsonElement("http://ip-api.com/json/").getAsJsonObject();
            if (!location.has("lat") || !location.has("lon")) return;
            double latitude = location.get("lat").getAsDouble();
            double longitude = location.get("lon").getAsDouble();
            try {
                JsonObject json = NetworkUtils.getJsonElement(
                        "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&hourly=weathercode&timezone=" + URLEncoder.encode(Calendar.getInstance().getTimeZone().getID(), StandardCharsets.UTF_8.toString())
                ).getAsJsonObject();
                for (int i = 0; i < json.get("hourly").getAsJsonObject().get("time").getAsJsonArray().size(); i++) {
                    String value = json.get("hourly").getAsJsonObject().get("time").getAsJsonArray().get(i).getAsString();
                    WMOCode code = WMOCode.fromCode((int) json.get("hourly").getAsJsonObject().get("weathercode").getAsJsonArray().get(i).getAsFloat());
                    data.put(Instant.parse(value + ":00Z"), code);
                }
                // Check if we have the data, so we don't re-fetch later
                if (data.containsKey(getThisHour()) && data.containsKey(getNextHour())) fetching = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static boolean isRaining() {
        return getRainStrength() > 0.05;
    }

    public static boolean isThundering() {
        return getThunderStrength() > 0.05;
    }

    public static boolean isSnowing() {
        return get(getThisHour()).snow;
    }

    public static float getRainStrength() {
        return interpolate(get(getThisHour()).rainStrength, get(getNextHour()).rainStrength, getTime());
    }

    public static float getThunderStrength() {
        return interpolate(get(getThisHour()).thunderStrength, get(getNextHour()).thunderStrength, getTime());
    }

    private static WMOCode get(Instant instant) {
        if (!data.containsKey(instant)) {
            fetchData();
            return WMOCode.CLEAR;
        }
        return data.get(instant);
    }

    private static Instant getThisHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.toInstant();
    }

    private static Instant getNextHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.HOUR, 1);
        return calendar.toInstant();
    }

    private static float getTime() {
        return Calendar.getInstance().get(Calendar.MINUTE) / 60f
                + Calendar.getInstance().get(Calendar.SECOND) / 3_600f
                + Calendar.getInstance().get(Calendar.MILLISECOND) / 3_600_000f;
    }

    private static float interpolate(float f1, float f2, float t) {
        return f1 * (1 - t) + f2 * t;
    }
}

package Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.time.LocalDate;

/**
 * Gson preconfigurado con adaptadores para java.time.LocalDate.
 * Serializa LocalDate como "YYYY-MM-DD" en lugar del objeto anidado
 * que produce Gson por defecto, incompatible con Java 21.
 */
public class GsonUtils {

    private static final Gson INSTANCE = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, type, ctx) -> LocalDate.parse(json.getAsString()))
            .create();

    public static Gson get() {
        return INSTANCE;
    }
}

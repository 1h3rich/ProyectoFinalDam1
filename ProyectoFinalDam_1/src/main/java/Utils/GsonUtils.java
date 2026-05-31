package Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.time.LocalDate;

/**
 * Gson preconfigurado con adaptadores para {@code java.time.LocalDate}.
 * Serializa {@code LocalDate} como {@code "YYYY-MM-DD"} en lugar del objeto anidado
 * que produce Gson por defecto, incompatible con Java 21.
 *
 * @author isard
 * @version 1.0
 */
public class GsonUtils {

    /** No instanciar: clase de utilidad estática. */
    private GsonUtils() {}

    private static final Gson INSTANCE = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, type, ctx) -> LocalDate.parse(json.getAsString()))
            .create();

    /**
     * Retorna la instancia preconfigurada de Gson con soporte para LocalDate.
     *
     * @return Instancia singleton de Gson.
     */
    public static Gson get() {
        return INSTANCE;
    }
}

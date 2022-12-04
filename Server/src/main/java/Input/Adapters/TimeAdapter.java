package Input.Adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.ZonedDateTime;

public class TimeAdapter extends TypeAdapter<ZonedDateTime> {

    @Override
    public ZonedDateTime read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        String time = reader.nextString();
        return ZonedDateTime.parse(time);
    }

    @Override
    public void write(JsonWriter writer, ZonedDateTime value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        String time = value.toString();
        writer.value(time);
    }
}
package net.groundmc.extensions.gson

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.bukkit.Location
import org.joda.time.DateTime
import java.lang.reflect.Type

private val gson = GsonBuilder().create()

/**
 * An adapter to serialize and deserialize [Location] objects.
 *
 * @author GiantTree
 * @since 1.0
 */
object LocationTypeAdapter : TypeAdapter<Location>() {

    private val mapStringAnyType: Type = TypeToken.getParameterized(Map::class.java, String::class.java, Any::class.java).type

    /**
     * Writes a [Location] object to a [JsonWriter].
     *
     * @param writer the [JsonWriter] to write to.
     * @param location the [Location] to write.
     */
    override fun write(writer: JsonWriter, location: Location?) {
        when {
            location != null -> writer.value(gson.toJson(location.serialize()))
            else -> writer.nullValue()
        }
    }

    /**
     * Reads a JSON-encoded string and converts it to a [Location] object.
     *
     * @param reader the [JsonReader] to read from.
     *
     * @return the deserialized [Location] object
     */
    override fun read(reader: JsonReader): Location? {
        val read = reader.nextString()

        return Location.deserialize(gson.fromJson<Map<String, Any>>(read, mapStringAnyType) as Map<String, Any>)
    }
}

/**
 * An adapter to serialize and deserialize [DateTime] objects.
 *
 * @author GiantTree
 * @since 1.0
 */
object DateTimeAdapter : TypeAdapter<DateTime>() {

    /**
     * Writes a [Location] object to a [JsonWriter].
     *
     * @param writer the [JsonWriter] to write to.
     * @param dateTime the [DateTime] to write.
     */
    override fun write(writer: JsonWriter, dateTime: DateTime?) {
        when {
            dateTime != null -> writer.value(dateTime.toString())
            else -> writer.nullValue()
        }
    }

    /**
     * Reads a JSON-encoded string and converts it to a [DateTime] object.
     *
     * @param reader the [JsonReader] to read from.
     *
     * @return the deserialized [DateTime] object
     */
    override fun read(reader: JsonReader): DateTime {
        return DateTime(reader.nextString())
    }

}

package net.groundmc.extensions.exposed

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.groundmc.extensions.gson.DateTimeAdapter
import org.bukkit.Location
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.VarCharColumnType
import org.joda.time.DateTime
import java.lang.reflect.Type

private val gson = GsonBuilder()
        .registerTypeAdapter(DateTime::class.java, DateTimeAdapter)
        .create()

/**
 * A profile property set column to [Location]s using a
 * varchar column.
 *
 * @param name the name of the column
 */
fun Table.location(name: String) = registerColumn<Location>(name, LocationColumnType())

/**
 * Column that stores [Location] in a varchar column of length 255.
 *
 * @constructor Constructs a new column to store locations in.
 *
 * @author GiantTree
 * @since 1.0
 */
class LocationColumnType : VarCharColumnType(255) {

    private val mapStringAnyType: Type = TypeToken.getParameterized(Map::class.java, String::class.java, Any::class.java).type

    override fun nonNullValueToString(value: Any): String {
        return when (value) {
            is Location -> gson.toJson(value.serialize())
            else -> super.nonNullValueToString(value)
        }
    }

    override fun notNullValueToDB(value: Any): Any {
        return when (value) {
            is Location -> gson.toJson(value.serialize())
            else -> super.notNullValueToDB(value)
        }
    }

    override fun valueFromDB(value: Any): Any {
        return when (value) {
            is String -> Location.deserialize(gson.fromJson(value, mapStringAnyType))
            else -> super.valueFromDB(value)
        }
    }

    override fun valueToString(value: Any?): String {
        return when {
            value != null -> nonNullValueToString(value)
            else -> super.valueToString(value)
        }
    }

    override fun valueToDB(value: Any?): Any? {
        return when {
            value != null -> notNullValueToDB(value)
            else -> super.valueToDB(value)
        }
    }
}

package net.groundmc.extensions.exposed

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.bukkit.Location
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.VarCharColumnType
import java.lang.reflect.Type

private val gson = GsonBuilder()
        .create()

/**
 * A profile property set column to [Location]s using a
 * varchar column.
 *
 * @param name the name of the column
 */
fun Table.location(name: String) = registerColumn<Location>(name, LocationColumnType())

/**
 * Column that stores a [Location] in a varchar column of length 255.
 *
 * @constructor Constructs a new column to store locations in.
 *
 * @since 1.0
 */
class LocationColumnType : VarCharColumnType(255) {

    private val mapStringAnyType: Type = object : TypeToken<Map<String, Any>>() {}.type

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

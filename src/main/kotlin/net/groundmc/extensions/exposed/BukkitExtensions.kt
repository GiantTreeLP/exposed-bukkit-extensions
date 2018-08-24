package net.groundmc.extensions.exposed

import com.google.gson.GsonBuilder
import net.groundmc.extensions.gson.DateTimeAdapter
import net.groundmc.extensions.gson.LocationTypeAdapter
import org.bukkit.Location
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.VarCharColumnType
import org.joda.time.DateTime

private val gson = GsonBuilder()
        .registerTypeAdapter(Location::class.java, LocationTypeAdapter)
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

    override fun nonNullValueToString(value: Any): String {
        return when (value) {
            is Location -> gson.toJson(value)
            else -> super.nonNullValueToString(value)
        }
    }

    override fun notNullValueToDB(value: Any): Any {
        return when (value) {
            is Location -> gson.toJson(value)
            else -> super.notNullValueToDB(value)
        }
    }

    override fun valueFromDB(value: Any): Any {
        return when (value) {
            is String -> gson.fromJson(value, Location::class.java)
            else -> super.valueFromDB(value)
        }
    }

    override fun valueToString(value: Any?): String {
        return when {
            value != null -> nonNullValueToString(value)
            else -> super.valueToString(value)
        }
    }
}

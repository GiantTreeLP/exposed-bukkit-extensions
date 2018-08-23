package net.groundmc.extensions.exposed

import com.destroystokyo.paper.profile.ProfileProperty
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.groundmc.extensions.gson.ProfilePropertySetTypeAdapter
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.VarCharColumnType
import java.lang.reflect.Type

val profilePropertySetType: Type = TypeToken.getParameterized(Set::class.java, ProfileProperty::class.java).type

private val gson = GsonBuilder()
        .registerTypeAdapter(profilePropertySetType, ProfilePropertySetTypeAdapter)
        .create()

fun Table.profileProperySet(name: String, length: Int, collate: String? = null) = registerColumn<Set<ProfileProperty>>(name, PropertySetColumnType(length, collate))

class PropertySetColumnType(length: Int, collate: String?) : VarCharColumnType(length, collate) {
    override fun nonNullValueToString(value: Any): String {
        return when (value) {
            is Set<*> -> gson.toJson(value)
            else -> super.nonNullValueToString(value)
        }
    }

    override fun valueFromDB(value: Any): Any {
        if (value is String) {
            return gson.fromJson(value, profilePropertySetType)
        }
        return super.valueFromDB(value)
    }

    override fun notNullValueToDB(value: Any): Any {
        if (value is Set<*>) {
            return gson.toJson(value)
        }
        return super.notNullValueToDB(value)
    }

    override fun valueToDB(value: Any?): Any? {
        if (value != null) {
            return notNullValueToDB(value)
        }
        return super.valueToDB(value)
    }

    override fun valueToString(value: Any?): String {
        if (value != null) {
            nonNullValueToString(value)
        }
        return super.valueToString(value)
    }
}

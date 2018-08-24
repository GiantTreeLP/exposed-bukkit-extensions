package net.groundmc.extensions.gson

import com.destroystokyo.paper.profile.ProfileProperty
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

/**
 * Type adapter to serialize and deserialize Sets of [ProfileProperty].
 *
 * @author GiantTree
 * @since 1.0
 */
object ProfilePropertySetTypeAdapter : TypeAdapter<Set<ProfileProperty>>() {
    override fun write(out: JsonWriter, value: Set<ProfileProperty>) {
        out.beginArray()
        value.forEach {
            out.beginObject().name("name").value(it.name)
                    .name("value").value(it.value)
            if (it.isSigned) {
                out.name("signature").value(it.signature)
            }
            out.endObject()
        }
        out.endArray()
    }

    override fun read(reader: JsonReader): Set<ProfileProperty> {
        val set = mutableSetOf<ProfileProperty>()
        reader.beginArray()
        while (reader.hasNext()) {
            reader.beginObject()
            val builder = ProfilePropertyBuilder()
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "name" -> builder.name = reader.nextString()
                    "value" -> builder.value = reader.nextString()
                    "signature" -> builder.signature = reader.nextString()
                    else -> reader.skipValue()
                }
            }
            set += builder.build() ?: continue
            reader.endObject()
        }
        reader.endArray()
        return set
    }
}

/**
 * Internal builder class for [ProfileProperty].
 *
 * @param name the name of the property
 * @param value the value of the property
 * @param signature a signature, if available
 */
internal class ProfilePropertyBuilder(var name: String? = null, var value: String? = null, var signature: String? = null) {

    /**
     * Builds a [ProfileProperty], if at least `name` and `value` are set
     *
     * @return the newly created profile property or `null`, if not all required
     * fields are set.
     */
    fun build() = if (name != null && value != null) {
        ProfileProperty(name!!, value!!, signature)
    } else {
        null
    }
}

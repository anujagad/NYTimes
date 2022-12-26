package com.anusha.nytimes.network.serializer

import com.google.gson.*
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatterBuilder
import java.lang.reflect.Type

class LocalTimeSerializer : JsonDeserializer<LocalTime>, JsonSerializer<LocalTime> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): LocalTime {
        return try {
            LocalTime.parse(json.asString, dateTimeFormatter)
        } catch (exception: Exception) {
            LocalTime.parse(json.asString, fallbackDateTimeFormatter)
        }
    }

    override fun serialize(src: LocalTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.format(dateTimeFormatter) ?: "")
    }

    companion object {
        private val dateTimeFormatter = DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("HH:mm").toFormatter()
        private val fallbackDateTimeFormatter = DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("H:mm").toFormatter()
    }
}
package com.anusha.nytimes.network.serializer

import com.anusha.nytimes.utils.Constants.SHORT_TIME_SLOT_DATE
import com.google.gson.*
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type

class LocalDateSerializerDeserializer : JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    override fun serialize(src: LocalDate?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.format(formatter) ?: "")
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDate {
        return LocalDate.parse(json?.asString)
    }

    companion object{
        private val formatter = DateTimeFormatter.ofPattern(SHORT_TIME_SLOT_DATE)
    }
}
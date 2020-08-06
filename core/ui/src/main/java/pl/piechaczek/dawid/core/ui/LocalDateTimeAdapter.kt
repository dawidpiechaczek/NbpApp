package pl.piechaczek.dawid.core.ui

import com.google.gson.internal.bind.util.ISO8601Utils
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import java.text.ParsePosition


class LocalDateTimeAdapter {

    @FromJson
    fun decode(dateString: String): LocalDateTime {
        val date = ISO8601Utils.parse(dateString, ParsePosition(0))
        return DateTimeUtils.toInstant(date).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }

    @ToJson
    fun encode(localDateTime: LocalDateTime): String {
        val date = DateTimeUtils.toDate(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
        return ISO8601Utils.format(date, true)
    }
}
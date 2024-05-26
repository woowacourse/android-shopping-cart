package woowacourse.shopping.data

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {
    @TypeConverter
    fun localDateTimeToJson(dateTime: LocalDateTime): String {
        return dateTime.format(
            DateTimeFormatter.ofPattern(DATE_TIME_PATTERN),
        ).toString()
    }

    @TypeConverter
    fun jsonToLocalDateTime(dateTime: String): LocalDateTime {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN))
    }

    companion object {
        private const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
    }
}

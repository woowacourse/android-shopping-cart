package woowacourse.shopping.data.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

object Converter {
    @TypeConverter
    fun toLong(value: LocalDateTime): Long {
        return value.toInstant(ZoneOffset.UTC).toEpochMilli()
    }

    @TypeConverter
    fun toLocalDate(value: Long): LocalDateTime {
        return value.let { LocalDateTime.ofEpochSecond(it / 1000, 0, ZoneOffset.UTC) }
    }
}

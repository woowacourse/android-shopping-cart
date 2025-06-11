package woowacourse.shopping.data.product

import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateConverters {
    @TypeConverter
    fun fromLocalDate(value: LocalDateTime): String = value.toString()

    @TypeConverter
    fun toLocalDate(value: String): LocalDateTime = LocalDateTime.parse(value)
}

package woowacourse.shopping.local.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime

class ShoppingConverters {
    @TypeConverter
    fun parseLocalDateTime(value: String): LocalDateTime {
        return LocalDateTime.parse(value)
    }

    @TypeConverter
    fun formatLocalDateTime(date: LocalDateTime): String {
        return date.toString()
    }
}

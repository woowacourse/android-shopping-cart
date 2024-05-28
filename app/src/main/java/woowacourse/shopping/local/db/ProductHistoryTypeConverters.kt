package woowacourse.shopping.local.db

import androidx.room.TypeConverter
import java.time.LocalDateTime

class ProductHistoryTypeConverters {
    @TypeConverter
    fun toLocalDateTime(localDateTime: LocalDateTime?): String? {
        return localDateTime?.toString()
    }

    @TypeConverter
    fun fromLocalDateTime(localDateTimeStr: String?): LocalDateTime? {
        return localDateTimeStr?.let { LocalDateTime.parse(it) }
    }
}

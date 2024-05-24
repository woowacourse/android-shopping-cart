package woowacourse.shopping.data.recentproduct

import androidx.room.TypeConverter
import java.time.LocalDateTime

class RecentProductTypeConverters {
    @TypeConverter
    fun fromLocalDateTime(screeningStartDateTime: LocalDateTime): String {
        return screeningStartDateTime.toString()
    }

    @TypeConverter
    fun toLocalDateTime(screeningStartDateTime: String): LocalDateTime {
        return LocalDateTime.parse(screeningStartDateTime)
    }
}

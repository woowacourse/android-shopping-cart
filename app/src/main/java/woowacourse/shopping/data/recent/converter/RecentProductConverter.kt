package woowacourse.shopping.data.recent.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime

class RecentProductConverter {
    @TypeConverter
    fun convertSeenDateTimeToString(seenDateTime: LocalDateTime): String {
        return seenDateTime.toString()
    }

    @TypeConverter
    fun convertStringToSeenDateTime(seenDateTimeValue: String): LocalDateTime {
        return LocalDateTime.parse(seenDateTimeValue)
    }
}

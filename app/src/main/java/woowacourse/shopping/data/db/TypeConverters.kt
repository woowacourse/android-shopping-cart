package woowacourse.shopping.data.db

import androidx.room.TypeConverter
import java.util.UUID

class ShoppingTypeConverters {
    @TypeConverter
    fun fromString(value: String?): UUID? = value?.let {
        UUID.fromString(it)
    }

    @TypeConverter
    fun toString(uuid: UUID?): String? = uuid?.toString()
}

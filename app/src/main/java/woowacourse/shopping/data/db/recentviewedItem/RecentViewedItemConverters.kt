package woowacourse.shopping.data.db.recentviewedItem

import androidx.room.TypeConverter
import com.google.gson.Gson
import woowacourse.shopping.domain.model.Product
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecentViewedItemConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime): String {
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @TypeConverter
    fun toLocalDateTime(dateTime: String): LocalDateTime {
        return LocalDateTime.parse(dateTime)
    }

    @TypeConverter
    fun fromProduct(product: Product): String {
        return gson.toJson(product)
    }

    @TypeConverter
    fun toProduct(product: String): Product {
        return gson.fromJson(product, Product::class.java)
    }
}

package woowacourse.shopping.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import woowacourse.shopping.model.Product
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {
    private val gson = Gson()

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

    @TypeConverter
    fun productToJson(product: Product): String {
        return gson.toJson(product)
    }

    @TypeConverter
    fun jsonToProduct(product: String): Product {
        return gson.fromJson(product, Product::class.java)
    }

    companion object {
        private const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
    }
}

package woowacourse.shopping.data.local.converter

import androidx.room.TypeConverter
import woowacourse.shopping.domain.Price

class PriceConverter {
    @TypeConverter
    fun fromPrice(price: Price): Int = price.value

    @TypeConverter
    fun toPrice(value: Int): Price = Price(value)
}

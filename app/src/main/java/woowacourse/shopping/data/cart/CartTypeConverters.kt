package woowacourse.shopping.data.cart

import androidx.room.TypeConverter
import woowacourse.shopping.model.Quantity

class CartTypeConverters {
    @TypeConverter
    fun fromQuantity(quantity: Quantity): Int {
        return quantity.value
    }

    @TypeConverter
    fun toQuantity(value: Int): Quantity {
        return Quantity(value)
    }
}

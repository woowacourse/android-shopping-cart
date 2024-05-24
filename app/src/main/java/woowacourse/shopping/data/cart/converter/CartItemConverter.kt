package woowacourse.shopping.data.cart.converter

import androidx.room.TypeConverter
import woowacourse.shopping.model.Quantity

class CartItemConverter {
    @TypeConverter
    fun convertQuantityToInt(quantity: Quantity): Int {
        return quantity.count
    }

    @TypeConverter
    fun convertIntToQuantity(quantityCount: Int): Quantity {
        return Quantity(quantityCount)
    }
}

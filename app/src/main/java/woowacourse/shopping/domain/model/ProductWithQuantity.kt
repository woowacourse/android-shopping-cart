package woowacourse.shopping.domain.model

import androidx.room.Embedded
import woowacourse.shopping.data.model.ProductEntity

data class ProductWithQuantity(
    @Embedded val product: ProductEntity,
    private val _quantity: Int,
) {
    var quantity: Int = _quantity
        private set

    fun totalPrice(): Long {
        return product.price * quantity
    }

    fun updateQuantity(newQuantity: Int) {
        quantity = newQuantity
    }
}

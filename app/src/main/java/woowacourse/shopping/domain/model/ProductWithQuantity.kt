package woowacourse.shopping.domain.model

import androidx.room.Embedded
import woowacourse.shopping.data.model.ProductEntity

data class ProductWithQuantity(
    @Embedded val product: ProductEntity,
    val quantity: Int,
) {
    fun totalPrice(): Long {
        return product.price * quantity
    }
}

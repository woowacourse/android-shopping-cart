package woowacourse.shopping.data.model.product

import androidx.room.Embedded
import androidx.room.Relation
import woowacourse.shopping.data.database.cart.CartContract
import woowacourse.shopping.data.database.product.ProductContract
import woowacourse.shopping.data.model.cart.CartItem

data class CartableProduct(
    @Embedded val product: Product,
    @Relation(
        parentColumn = ProductContract.COLUMN_ID,
        entityColumn = CartContract.COLUMN_PRODUCT_ID,
    )
    val cartItem: CartItem? = null,
) {
    val quantity: Int
        get() = cartItem?.quantity ?: 0
}

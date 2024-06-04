package woowacourse.shopping.data.model.cart

import androidx.room.Embedded
import androidx.room.Relation
import woowacourse.shopping.data.database.cart.CartContract
import woowacourse.shopping.data.database.product.ProductContract
import woowacourse.shopping.data.model.product.Product

data class CartedProduct(
    @Embedded val cartItem: CartItem,
    @Relation(
        parentColumn = CartContract.COLUMN_PRODUCT_ID,
        entityColumn = ProductContract.COLUMN_ID,
    )
    val product: Product,
)

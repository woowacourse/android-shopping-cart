package woowacourse.shopping.data.cart

import androidx.room.Embedded
import androidx.room.Relation
import woowacourse.shopping.data.product.ProductEntity

class CartItemDetail(
    @Embedded val cartItem: CartItemEntity,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "id",
    )
    val productEntity: ProductEntity,
)

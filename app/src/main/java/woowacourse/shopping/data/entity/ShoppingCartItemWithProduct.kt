package woowacourse.shopping.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ShoppingCartItemWithProduct(
    @Embedded
    val shoppingCartItem: ShoppingCartItemEntity,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "id",
    )
    val product: ProductEntity,
)

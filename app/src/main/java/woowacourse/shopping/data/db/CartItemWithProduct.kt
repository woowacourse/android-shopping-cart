package woowacourse.shopping.data.db

import androidx.room.Embedded
import androidx.room.Relation
import woowacourse.shopping.data.entity.CartEntity
import woowacourse.shopping.data.entity.CatalogEntity

data class CartItemWithProduct(
    @Embedded val cartItem: CartEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "productId"
    )
    val product: CatalogEntity
)

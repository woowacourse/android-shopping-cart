package woowacourse.shopping.data.db

import androidx.room.Embedded
import androidx.room.Relation
import woowacourse.shopping.data.entity.CartEntity
import woowacourse.shopping.data.entity.CatalogEntity

data class CartItemWithProduct(
    @Embedded val cartEntity: CartEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "productId"
    )
    val catalogEntity: CatalogEntity
)

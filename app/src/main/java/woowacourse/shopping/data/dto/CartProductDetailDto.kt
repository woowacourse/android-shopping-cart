package woowacourse.shopping.data.dto

import androidx.room.Embedded
import androidx.room.Relation
import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.data.entity.ProductEntity

data class CartProductDetailDto(
    @Embedded val cartProduct: CartProductEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id",
    )
    val product: ProductEntity,
)

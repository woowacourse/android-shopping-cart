package woowacourse.shopping.data.model.response

import androidx.room.Embedded
import androidx.room.Relation
import woowacourse.shopping.data.model.entity.CartProductEntity
import woowacourse.shopping.data.model.entity.ProductEntity

data class CartProductDetailResponse(
    @Embedded val cartProduct: CartProductEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id",
    )
    val product: ProductEntity?,
)

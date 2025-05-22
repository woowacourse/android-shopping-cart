package woowacourse.shopping.data

import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

object CartMapper {
    fun CartItem.toEntity(): CartEntity =
        CartEntity(
            productId = this.product.productId,
            quantity = 1,
        )

    fun Product.toEntity(quantity: Int): CartEntity =
        CartEntity(
            productId = this.productId,
            quantity = quantity,
        )
}

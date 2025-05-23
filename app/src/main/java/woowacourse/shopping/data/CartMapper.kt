package woowacourse.shopping.data

import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.domain.model.CartItem

object CartMapper {
    fun CartItem.toEntity(): CartEntity =
        CartEntity(
            productId = this.product.productId,
            quantity = 1,
        )
}

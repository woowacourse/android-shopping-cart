package woowacourse.shopping.data

import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.domain.model.Product

object CartMapper {
//    fun CartEntity.toDomain(): CartItem =
//        CartItem(
//            product = this.,
//
//        )

    fun Product.toEntity(quantity: Int): CartEntity =
        CartEntity(
            productId = this.productId,
            quantity = quantity,
        )
}

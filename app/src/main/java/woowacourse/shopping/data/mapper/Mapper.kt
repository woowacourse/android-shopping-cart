package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

fun CartEntity.toCartItem() = CartItem(cartId, productId, quantity)

fun Product.toProductEntity() = CartEntity(productId = id)

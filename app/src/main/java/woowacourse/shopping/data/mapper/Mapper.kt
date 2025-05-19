package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.domain.model.Product

fun Product.toProductEntity() = CartEntity(productId = id)

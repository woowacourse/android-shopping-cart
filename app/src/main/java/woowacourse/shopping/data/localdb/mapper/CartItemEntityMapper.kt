package woowacourse.shopping.data.localdb.mapper

import woowacourse.shopping.data.localdb.entity.CartItemEntity
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

fun CartItemEntity.toDomain(product: Product): CartItem =
    CartItem(
        product = product,
        quantity = quantity,
    )

fun CartItem.toEntity(timestamp: Long): CartItemEntity =
    CartItemEntity(
        id = product.id,
        quantity = quantity,
        timestamp = timestamp,
    )

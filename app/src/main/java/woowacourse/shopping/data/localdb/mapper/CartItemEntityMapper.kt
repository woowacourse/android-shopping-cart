package woowacourse.shopping.data.localdb.mapper

import woowacourse.shopping.data.localdb.entity.CartItemEntity
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

fun CartItemEntity.toDomain(product: Product): CartItem {
    require(id == product.id) { "id가 일치하지 않습니다." }

    return CartItem(
        product = product,
        quantity = quantity,
    )
}

fun CartItem.toEntity(timestamp: Long): CartItemEntity =
    CartItemEntity(
        id = product.id,
        quantity = quantity,
        timestamp = timestamp,
    )

package woowacourse.shopping.data

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Goods

fun Goods.toEntity(quantity: Int = 0): CartEntity =
    CartEntity(
        name = name,
        price = price,
        thumbnailUrl = thumbnailUrl,
        id = id,
        quantity = quantity,
    )

fun CartEntity.toDomainGoods(): Goods = Goods(name = name, price = price, thumbnailUrl = thumbnailUrl, id = id)

fun CartEntity.toDomainCartItem(): CartItem = CartItem(goods = this.toDomainGoods(), quantity = quantity)

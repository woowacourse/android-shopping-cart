package woowacourse.shopping.data

import woowacourse.shopping.data.cart.CartEntity
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Goods

fun Cart.toEntity(): CartEntity = CartEntity(id = goods.id, name = goods.name, price = goods.price, thumbnailUrl = goods.thumbnailUrl)

fun CartEntity.toDomain(): Cart = Cart(goods = Goods(id = id, name = name, price = price, thumbnailUrl = thumbnailUrl), quantity = quantity)

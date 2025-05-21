package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Goods

fun Goods.toEntity(): CartEntity = CartEntity(id = id, name = name, price = price, thumbnailUrl = thumbnailUrl, quantity = quantity)

fun CartEntity.toDomain(): Goods = Goods(id = id, name = name, price = price, thumbnailUrl = thumbnailUrl)

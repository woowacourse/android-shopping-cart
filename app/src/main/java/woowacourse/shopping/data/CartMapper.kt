package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Goods

fun Goods.toEntity(): CartEntity = CartEntity(name = name, price = price, thumbnailUrl = thumbnailUrl, id = id)

fun CartEntity.toDomain(): Goods = Goods(name = name, price = price, thumbnailUrl = thumbnailUrl, id = id)

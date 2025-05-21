package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Goods

fun Goods.toEntity(quantity: Int = 0): CartEntity =
    CartEntity(
        name = name,
        price = price,
        thumbnailUrl = thumbnailUrl,
        id = id,
        quantity = quantity,
    )

fun CartEntity.toDomain(): Goods = Goods(name = name, price = price, thumbnailUrl = thumbnailUrl, id = id)

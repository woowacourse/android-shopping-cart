package woowacourse.shopping.data.localdb.mapper

import woowacourse.shopping.data.localdb.entity.RecentItemEntity
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductName

fun RecentItemEntity.toDomain(): Product =
    Product(
        id = id,
        name = ProductName(name),
        price = Money(price),
        imageUrl = imageUrl,
    )

fun Product.toEntity(timestamp: Long): RecentItemEntity =
    RecentItemEntity(
        id = id,
        name = name.name,
        price = price.amount,
        imageUrl = imageUrl,
        timestamp = timestamp,
    )

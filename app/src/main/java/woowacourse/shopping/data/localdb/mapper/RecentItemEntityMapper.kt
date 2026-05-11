package woowacourse.shopping.data.localdb.mapper

import woowacourse.shopping.data.localdb.entity.RecentItemEntity
import woowacourse.shopping.model.Product

fun toDomain(product: Product): Product = product

fun Product.toEntity(timestamp: Long): RecentItemEntity =
    RecentItemEntity(
        id = id,
        timestamp = timestamp,
    )

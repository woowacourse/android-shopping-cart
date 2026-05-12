package woowacourse.shopping.data.localdb.mapper

import woowacourse.shopping.data.localdb.entity.RecentItemEntity
import woowacourse.shopping.model.Product

fun RecentItemEntity.toDomain(product: Product): Product {
    require(id == product.id) { "id가 일치하지 않습니다." }

    return product
}

fun Product.toEntity(timestamp: Long): RecentItemEntity =
    RecentItemEntity(
        id = id,
        timestamp = timestamp,
    )

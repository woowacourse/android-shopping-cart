package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.ProductEntity
import woowacourse.shopping.domain.Product

fun List<ProductEntity>.toDomain() =
    this.map { entity ->
        Product(
            imageUrl = entity.imageUrl,
            name = entity.name,
            price = entity.price,
        )
    }

fun Product.toEntity() =
    ProductEntity(
        imageUrl = this.imageUrl,
        name = this.name,
        price = this.price,
    )

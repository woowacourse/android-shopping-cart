package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.dummy.ProductData
import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.domain.Product

fun List<ProductEntity>.toDomain() =
    this.map { entity ->
        Product(
            id = entity.id,
            imageUrl = entity.imageUrl,
            name = entity.name,
            price = entity.price,
        )
    }

fun Product.toEntity() =
    ProductEntity(
        id = this.id,
        imageUrl = this.imageUrl,
        name = this.name,
        price = this.price,
    )

fun Long.toProduct(): Product = ProductData.products.find { it.id == this } ?: throw IllegalArgumentException()

fun Product.toId(): Long = ProductData.products.find { it == this }?.id ?: throw IllegalArgumentException()

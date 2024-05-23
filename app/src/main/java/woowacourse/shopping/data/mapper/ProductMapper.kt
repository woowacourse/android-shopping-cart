package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.domain.model.Product

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
    )
}

fun ProductEntity.toDomainModel(): Product {
    return Product(
        id = this.id,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
    )
}

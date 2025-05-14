package woowacourse.shopping.utils

import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.domain.product.Money
import woowacourse.shopping.domain.product.Product

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        imageUrl = this.imageUrl,
        name = this.name,
        price = this.price,
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        imageUrl = this.imageUrl,
        name = this.name,
        _price = Money(this.price),
    )
}

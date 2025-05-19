package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.db.ProductEntity
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product

fun ProductEntity.toProduct() = Product(id, name, imageUrl, Price(price))

fun Product.toProductEntity() = ProductEntity(id, name, imageUrl, price.value)

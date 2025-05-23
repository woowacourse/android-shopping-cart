package woowacourse.shopping.data.product

import woowacourse.shopping.domain.Product

fun Product.toEntity(): ProductEntity = ProductEntity(id, name, price, 0, imageUrl)

fun ProductEntity.toDomain(): Product = Product(id, name, price, imageUrl)

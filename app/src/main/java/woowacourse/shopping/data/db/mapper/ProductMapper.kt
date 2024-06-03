package woowacourse.shopping.data.db.mapper

import woowacourse.shopping.data.db.model.ProductEntity
import woowacourse.shopping.domain.model.Product

fun ProductEntity.toProduct(): Product = Product(productId, name, price, imageUrl)

fun Product.toEntity(): ProductEntity = ProductEntity(id, name, price, imageUrl)

fun List<ProductEntity>.toProduct(): List<Product> =
    this.map {
        it.toProduct()
    }

fun List<Product>.toEntity(): List<ProductEntity> =
    this.map {
        it.toEntity()
    }

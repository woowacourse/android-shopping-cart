package woowacourse.shopping.data.db

import woowacourse.shopping.data.db.entity.ProductEntity
import woowacourse.shopping.domain.model.Product

fun ProductEntity.toProduct(): Product = Product(id, name, price, imageUrl)

fun Product.toEntity(): ProductEntity = ProductEntity(id, name, price, imageUrl)

fun List<ProductEntity>.toProduct(): List<Product> =
    this.map {
        it.toProduct()
    }

fun List<Product>.toEntity(): List<ProductEntity> =
    this.map {
        it.toEntity()
    }

package woowacourse.shopping.data.product.entity

import woowacourse.shopping.domain.product.Product

data class ProductEntity(
    val id: Long,
    val name: String,
    val price: Int,
) {

    fun toDomain(): Product {
        return Product(
            id = id,
            name = name,
            price = price,
        )
    }

    companion object {
        fun Product.toEntity(): ProductEntity {
            return ProductEntity(
                id = id,
                name = name,
                price = price,
            )
        }
    }
}


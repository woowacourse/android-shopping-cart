package woowacourse.shopping.data.product.entity

import woowacourse.shopping.domain.product.Product

data class ProductEntity(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
) {
    fun toDomain(): Product =
        Product(
            id = id,
            name = name,
            price = price,
            imageUrl = imageUrl,
        )
}

fun Product.toEntity(): ProductEntity =
    ProductEntity(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
    )

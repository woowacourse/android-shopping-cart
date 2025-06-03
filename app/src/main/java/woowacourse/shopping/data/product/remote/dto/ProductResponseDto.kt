package woowacourse.shopping.data.product.remote.dto

import woowacourse.shopping.domain.product.Product

data class ProductResponseDto(
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

package woowacourse.shopping.data.dto

import woowacourse.shopping.domain.model.Product

data class ProductDto(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
) {
    fun toDomain(): Product = Product(id, name, imageUrl, price)
}
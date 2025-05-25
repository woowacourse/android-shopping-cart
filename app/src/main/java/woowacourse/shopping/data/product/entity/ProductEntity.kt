package woowacourse.shopping.data.product.entity

import woowacourse.shopping.domain.product.Product

data class ProductEntity(
    val id: Long,
    val name: String,
    val price: Int,
) {
    fun toDomain(): Product = Product(id, name, price)

    companion object {
        fun Product.toEntity(): ProductEntity = ProductEntity(id, name, price)
    }
}

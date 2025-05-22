package woowacourse.shopping.data.network.dto

import woowacourse.shopping.domain.product.ProductSinglePage

data class ProductPageDto(
    val products: List<ProductDto>,
    val hasNext: Boolean,
) {
    fun toDomain(): ProductSinglePage {
        return ProductSinglePage(
            products = products.map { it.toDomain() },
            hasNextPage = hasNext,
        )
    }
}

package woowacourse.shopping.data.network.entitiy

import woowacourse.shopping.domain.product.ProductSinglePage

data class ProductPageEntity(
    val products: List<ProductEntity>,
    val hasNext: Boolean,
) {
    fun toDomain(): ProductSinglePage {
        return ProductSinglePage(
            products = products.map { it.toDomain() },
            hasNextPage = hasNext,
        )
    }
}

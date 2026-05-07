package woowacourse.shopping.domain

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

val SHOPPING_PAGE_SIZE = 20

data class Products(
    val products: List<Product> = emptyList(),
) {
    fun hasNextPage(
        currentPageIndex: Int,
        pageSize: Int = SHOPPING_PAGE_SIZE,
    ): Boolean =
        products
            .toPage(
                PageRequest(
                    index = currentPageIndex,
                    size = pageSize,
                ),
            ).hasNext

    @OptIn(ExperimentalUuidApi::class)
    fun findProductById(productId: Uuid): Product? = products.firstOrNull { it.productId == productId }
}

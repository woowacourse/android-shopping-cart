package woowacourse.shopping.domain.model.product

import kotlin.uuid.ExperimentalUuidApi

data class RecentlyViewedProducts(
    val productItems: List<Product> = emptyList(),
) {
    @OptIn(ExperimentalUuidApi::class)
    fun add(product: Product): RecentlyViewedProducts {
        val filtered =
            productItems.filterNot {
                it.productId == product.productId
            }

        return RecentlyViewedProducts(
            productItems = (listOf(product) + filtered).take(MAX_SIZE),
        )
    }

    companion object {
        private const val MAX_SIZE = 10
    }
}

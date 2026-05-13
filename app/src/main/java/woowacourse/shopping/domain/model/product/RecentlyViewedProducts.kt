package woowacourse.shopping.domain.model.product

data class RecentlyViewedProducts(
    val productItems: List<Product> = emptyList(),
) {
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

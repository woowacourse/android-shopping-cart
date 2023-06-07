package woowacourse.shopping.ui.products.uistate

import woowacourse.shopping.domain.Product

data class ProductUIState(
    val imageUrl: String,
    val name: String,
    val price: Int,
    val id: Long,
) {
    var count: Int = NO_COUNT
        private set

    fun updateCount(count: Int = NO_COUNT) {
        this.count = count
    }

    companion object {
        const val NO_COUNT = 0
        const val MINIMUM_COUNT = 1

        fun from(product: Product): ProductUIState =
            ProductUIState(product.imageUrl, product.name, product.price, product.id)
    }
}

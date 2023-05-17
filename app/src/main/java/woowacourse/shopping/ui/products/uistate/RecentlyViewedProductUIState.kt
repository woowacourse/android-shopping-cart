package woowacourse.shopping.ui.products.uistate

import woowacourse.shopping.domain.Product

data class RecentlyViewedProductUIState(
    val imageUrl: String,
    val name: String,
    val id: Long,
) {
    companion object {
        fun from(product: Product): RecentlyViewedProductUIState {
            return RecentlyViewedProductUIState(product.imageUrl, product.name, product.id)
        }
    }
}

package woowacourse.shopping.ui.products.uistate

import woowacourse.shopping.domain.RecentlyViewedProduct

data class RecentlyViewedProductUIState(
    val imageUrl: String,
    val name: String,
    val id: Long,
) {
    companion object {
        fun from(product: RecentlyViewedProduct): RecentlyViewedProductUIState {
            return RecentlyViewedProductUIState(product.imageUrl, product.name, product.id)
        }
    }
}

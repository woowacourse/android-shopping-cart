package woowacourse.shopping.ui.productdetail.uistate

import woowacourse.shopping.domain.RecentlyViewedProduct

data class LastViewedProductUIState(
    val name: String,
    val price: Int,
    val id: Long
) {

    companion object {
        fun from(recentlyViewedProduct: RecentlyViewedProduct): LastViewedProductUIState {
            return LastViewedProductUIState(
                recentlyViewedProduct.product.name,
                recentlyViewedProduct.product.price,
                recentlyViewedProduct.product.id
            )
        }
    }
}
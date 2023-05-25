package woowacourse.shopping.ui.products.uistate

import woowacourse.shopping.domain.RecentlyViewedProduct

data class RecentlyViewedProductUIState(
    val imageUrl: String,
    val name: String,
    val recentlyViewedProductId: Long,
    val productId: Long
) {
    companion object {
        fun from(recentlyViewedProduct: RecentlyViewedProduct): RecentlyViewedProductUIState {
            val product = recentlyViewedProduct.product
            val recentlyViewedProductId = recentlyViewedProduct.id
                ?: throw IllegalArgumentException("아이디가 부여되지 않은 최근 본 상품에 대한 UI 상태를 생성할 수 없습니다.")
            return RecentlyViewedProductUIState(
                product.imageUrl,
                product.name,
                recentlyViewedProductId,
                product.id
            )
        }
    }
}

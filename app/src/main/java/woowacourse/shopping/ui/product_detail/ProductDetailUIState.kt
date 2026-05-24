package woowacourse.shopping.ui.product_detail

import woowacourse.shopping.domain.Product

data class ProductDetailUIState(
    val amount: Int,
    val recentProducts: List<Product>,
) {
    val lastViewedProduct get() = if (recentProducts.isEmpty()) null else recentProducts.first()
    val isDecreaseEnable: Boolean get() = amount > 1
}

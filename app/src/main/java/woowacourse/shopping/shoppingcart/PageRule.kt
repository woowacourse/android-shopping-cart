package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.Page
import woowacourse.shopping.model.ShoppingCartProductUiModel

interface PageRule {

    fun getProductsOfPage(
        products: List<ShoppingCartProductUiModel>,
        page: Page
    ): List<ShoppingCartProductUiModel>

    fun getPageOfEnd(
        totalProductsSize: Int,
    ): Page
}

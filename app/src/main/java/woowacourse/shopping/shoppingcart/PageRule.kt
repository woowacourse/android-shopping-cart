package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.Page
import woowacourse.shopping.model.ProductUiModel

interface PageRule {

    val itemCountOnEachPage: Int

    fun getProductsOfPage(
        products: List<ProductUiModel>,
        page: Page
    ): List<ProductUiModel>

    fun getPageOfEnd(
        totalProductsSize: Int,
    ): Page
}

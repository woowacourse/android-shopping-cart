package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.Page
import woowacourse.shopping.model.ProductUiModel

interface ShowingRule {

    fun of(
        products: List<ProductUiModel>,
        page: Page,
    ): List<ProductUiModel>
}

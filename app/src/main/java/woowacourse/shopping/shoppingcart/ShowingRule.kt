package woowacourse.shopping.shoppingcart

import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.Page

interface ShowingRule {

    fun of(
        products: List<CartProductUiModel>,
        page: Page,
    ): List<CartProductUiModel>
}

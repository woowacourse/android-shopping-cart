package woowacourse.shopping.view.uimodel

import woowacourse.shopping.data.page.Page

data class ShoppingCartRecyclerViewItems(
    val page: Page<ShoppingCartItemUiModel>,
    val quantityInfo: QuantityInfo<ShoppingCartItemUiModel>,
)

package woowacourse.shopping.view.uimodel

import woowacourse.shopping.data.page.Page

data class MainRecyclerViewProduct(
    val page: Page<ProductUiModel>,
    val shoppingCartItemUiModels: List<ShoppingCartItemUiModel>,
    val quantityInfo: QuantityInfo<ProductUiModel>,
)

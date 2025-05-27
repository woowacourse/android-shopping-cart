package woowacourse.shopping.view.uimodel

import woowacourse.shopping.data.page.Page

data class MainRecyclerViewProduct(
    val page: Page<ProductUiModel>,
    val quantityInfo: QuantityInfo<ProductUiModel>,
)

package woowacourse.shopping.productlist

import woowacourse.shopping.ui.DisplayText

data class ProductUiModel(
    val id: String,
    val name: String,
    val price: DisplayText,
    val quantity: Int,
    val imageUrl: String,
)

package woowacourse.shopping.ui.screens.product

data class ProductUiModel(
    val id: String,
    val name: String,
    val price: String,
    val imageUrl: String,
    val cartAmount: String,
    val showAmountController: Boolean,
)

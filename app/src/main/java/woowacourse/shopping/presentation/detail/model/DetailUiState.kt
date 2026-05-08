package woowacourse.shopping.presentation.detail.model

import woowacourse.shopping.presentation.common.model.ProductUiModel

data class DetailUiState(
    val product: ProductUiModel =
        ProductUiModel(
            id = "",
            name = "",
            imageUrl = "",
            price = 0,
        ),
    val quantity: Int = 0,
) {
    val price: Long get() = product.price * quantity
}

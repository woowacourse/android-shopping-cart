package woowacourse.shopping.presentation.detail.model

import woowacourse.shopping.presentation.common.model.ProductUiModel

data class DetailUiState(
    val product: ProductUiModel =
        ProductUiModel(
            id = 0L,
            name = "",
            imageUrl = "",
            price = 0,
        ),
    val quantity: Int = 1,
    val lastSeenProduct: ProductUiModel? = null,
) {
    val price: Long get() = product.price * quantity
    val showLastSeenProductCard: Boolean get() = lastSeenProduct?.let { it.id != product.id } ?: false
}

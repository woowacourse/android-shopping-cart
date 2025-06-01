package woowacourse.shopping.view.detail.vm

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.main.state.ProductState

data class DetailUiState(
    val product: ProductState,
    val lastSeenProduct: Product?,
) {
    val productPrice: Int
        get() = product.item.priceValue * product.cartQuantity.value

    val hasLastSeenProduct: Boolean
        get() = lastSeenProduct != null
}

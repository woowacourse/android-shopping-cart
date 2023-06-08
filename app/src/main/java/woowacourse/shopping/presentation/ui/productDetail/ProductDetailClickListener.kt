package woowacourse.shopping.presentation.ui.productDetail

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.shoppingCart.uiModel.ProductInCartUiState

interface ProductDetailClickListener {

    fun setClickEventOnToShoppingCart(product: ProductInCartUiState)

    fun setClickEventOnLastViewed(lastViewedProduct: Product)
}

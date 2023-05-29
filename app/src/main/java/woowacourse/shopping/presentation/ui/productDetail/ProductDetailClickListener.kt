package woowacourse.shopping.presentation.ui.productDetail

import woowacourse.shopping.domain.model.Product

interface ProductDetailClickListener {

    fun setClickEventOnToShoppingCart(product: Product)
    fun setClickEventOnLastViewed(lastViewedProduct: Product)
}

package woowacourse.shopping.view.productDetail

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.common.ProductQuantityListener

interface ProductDetailListener : ProductQuantityListener {
    fun onCloseButtonClick()

    fun onAddingToShoppingCartClick()

    fun onRecentProductClick(product: Product)
}

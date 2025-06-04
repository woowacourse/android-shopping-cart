package woowacourse.shopping.view.common

import woowacourse.shopping.domain.product.Product

interface ProductQuantityListener {
    fun onPlusShoppingCartClick(product: Product)

    fun onMinusShoppingCartClick(product: Product)
}

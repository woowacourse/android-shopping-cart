package woowacourse.shopping.view.common

import woowacourse.shopping.domain.product.Product

interface ProductQuantityClickListener {
    fun onPlusShoppingCartClick(product: Product)

    fun onMinusShoppingCartClick(product: Product)
}

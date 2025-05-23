package woowacourse.shopping.view.product

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingProduct

interface OnProductListener {
    fun onItemClick(product: Product)

    fun onInitPlusButtonClick(product: Product)

    fun onQuantityControlPlusClick(product: Product)

    fun onQuantityControlMinusClick(shoppingProduct: ShoppingProduct)
}

package woowacourse.shopping.view.productDetail

import woowacourse.shopping.domain.product.Product

interface ProductDetailClickListener {
    fun onCloseButton()

    fun onAddingToShoppingCart()

    fun onPlusQuantity(product: Product)

    fun onMinusQuantity(product: Product)
}

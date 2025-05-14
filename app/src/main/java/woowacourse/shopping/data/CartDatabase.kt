package woowacourse.shopping.data

import woowacourse.shopping.product.catalog.ProductUiModel

object CartDatabase {
    private val _cartProducts: MutableList<ProductUiModel> = mutableListOf()
    val cartProducts: List<ProductUiModel> get() = _cartProducts.toList()

    fun insertCartProduct(cartProduct: ProductUiModel) {
        _cartProducts.add(cartProduct)
    }

    fun deleteCartProduct(cartProduct: ProductUiModel) {
        _cartProducts.remove(cartProduct)
    }
}

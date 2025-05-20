package woowacourse.shopping.data

import woowacourse.shopping.product.catalog.ProductUiModel

object CartDatabase : CartProductDataSource {
    private val _cartProducts: MutableList<ProductUiModel> = mutableListOf()
    val cartProducts: List<ProductUiModel> get() = _cartProducts.toList()

    override fun insertCartProduct(cartProduct: ProductUiModel) {
        _cartProducts.add(cartProduct)
    }

    override fun deleteCartProduct(cartProduct: ProductUiModel) {
        _cartProducts.remove(cartProduct)
    }

    override fun getCartProductsInRange(
        startIndex: Int,
        endIndex: Int,
    ): List<ProductUiModel> = cartProducts.subList(startIndex, endIndex)

    override fun getAllProductsSize(): Int = cartProducts.size
}

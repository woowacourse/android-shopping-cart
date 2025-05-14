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

    fun getCartProducts(
        page: Int,
        pageSize: Int = PAGE_SIZE,
    ): List<ProductUiModel> {
        val fromIndex = page * pageSize
        val toIndex = minOf(fromIndex + pageSize, cartProducts.size)
        val pagedProducts: List<ProductUiModel> =
            cartProducts.subList(fromIndex, toIndex)
        return pagedProducts
    }

    private const val PAGE_SIZE = 5
}

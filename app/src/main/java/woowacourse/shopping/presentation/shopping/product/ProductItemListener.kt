package woowacourse.shopping.presentation.shopping.product

interface ProductItemListener {
    fun increaseProductCount(id: Long)

    fun decreaseProductCount(id: Long)

    fun navigateToDetail(id: Long)

    fun loadProducts()
}
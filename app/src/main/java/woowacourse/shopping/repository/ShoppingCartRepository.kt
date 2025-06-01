package woowacourse.shopping.repository

interface ShoppingCartRepository {
    fun addCart(
        productId: Int,
        quantity: Int,
    )

    fun removeCart(productId: Int)
}

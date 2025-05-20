package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun fetchCartProducts(page: Int): List<Product>

    fun fetchMaxPageCount(): Int

    fun addCartProduct(product: Product)

    fun removeCartProduct(id: Int)

    fun clearCart()
}

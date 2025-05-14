package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.Product

object CartDummyRepositoryImpl {
    private val cart: MutableList<Product> = mutableListOf<Product>()

    fun fetchCartProducts(page: Int): List<Product> = cart.drop((page - 1) * 5).take(5)

    fun fetchMaxPageCount(): Int = if (cart.size % 5 == 0) cart.size / 5 else (cart.size / 5) + 1

    fun addCartProduct(product: Product) {
        cart.add(product.copy(id = (cart.maxOfOrNull { it.id } ?: 0) + 1))
    }

    fun removeCartProduct(id: Int) {
        cart.removeIf { it.id == id }
    }
}

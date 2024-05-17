package woowacourse.shopping.repository

import woowacourse.shopping.FiveCartItemPagingStrategy
import woowacourse.shopping.PagingStrategy
import woowacourse.shopping.db.Product

class FakeShoppingCartItemRepository(
    private val cartItems: MutableList<Product> = mutableListOf(),
    private val pagingStrategy: PagingStrategy<Product> = FiveCartItemPagingStrategy(),
) : ShoppingCartItemRepository {
    override fun addCartItem(product: Product): Int {
        val addedProduct = product.copy(id = cartItems.size + 1)
        cartItems.add(addedProduct)
        return addedProduct.id
    }

    override fun findById(id: Int): Product =
        cartItems.find { it.id == id } ?: throw NoSuchElementException("there is no product with id: $id")

    override fun loadPagedCartItems(page: Int): List<Product> = pagingStrategy.loadPagedData(page, cartItems)

    override fun removeCartItem(productId: Int): Product {
        val product = cartItems.find { it.id == productId } ?: throw NoSuchElementException()
        cartItems.remove(product)
        return product
    }

    override fun clearAllCartItems() {
        cartItems.clear()
    }

    override fun isFinalPage(page: Int): Boolean = pagingStrategy.isFinalPage(page, cartItems)
}

package woowacourse.shopping.repository

import woowacourse.shopping.NumberPagingStrategy
import woowacourse.shopping.PagingStrategy
import woowacourse.shopping.data.ProductData

class FakeShoppingCartItemRepository(
    private val cartItems: MutableList<ProductData> = mutableListOf(),
    private val pagingStrategy: PagingStrategy<ProductData> = NumberPagingStrategy(countPerLoad = 5),
) : ShoppingCartItemRepository {
    override fun addCartItem(product: ProductData): Int {
        val addedProduct = product.copy()
        cartItems.add(addedProduct)
        return addedProduct.id
    }

    override fun findById(id: Int): ProductData =
        cartItems.find { it.id == id } ?: throw NoSuchElementException("there is no product with id: $id")

    override fun loadPagedCartItems(page: Int): List<ProductData> = pagingStrategy.loadPagedData(page, cartItems)

    override fun removeCartItem(productId: Int): ProductData {
        val product = cartItems.find { it.id == productId } ?: throw NoSuchElementException()
        cartItems.remove(product)
        return product
    }

    override fun clearAllCartItems() {
        cartItems.clear()
    }

    override fun isFinalPage(page: Int): Boolean = pagingStrategy.isFinalPage(page, cartItems)
}

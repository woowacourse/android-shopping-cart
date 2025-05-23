package woowacourse.shopping.cart

import woowacourse.shopping.data.CartItem
import woowacourse.shopping.data.CartItemDao
import woowacourse.shopping.mapper.toCartItem
import woowacourse.shopping.mapper.toUiModel
import woowacourse.shopping.product.catalog.ProductUiModel

class CartItemRepository(
    private val dao: CartItemDao,
) {
    val allCartItemSize get() = dao.getAll().size

    fun getAllCartItem(): List<ProductUiModel> = dao.getAll().map { it.toUiModel() }

    fun subListCartItems(
        startIndex: Int,
        endIndex: Int,
    ): List<ProductUiModel> = getAllCartItem().subList(startIndex, endIndex)

    fun insertCartItem(product: ProductUiModel) {
        dao.insertCartItem(product.toCartItem())
    }

    fun updateCartItem(product: ProductUiModel) {
        dao.updateQuantity(product.id, product.quantity)
    }

    fun deleteCartItemById(productId: Long) {
        dao.deleteByProductId(productId)
    }

    fun findCartItem(product: ProductUiModel): CartItem? = dao.getCartItemById(product.id)
}

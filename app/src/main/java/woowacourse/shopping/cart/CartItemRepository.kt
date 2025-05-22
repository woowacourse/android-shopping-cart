package woowacourse.shopping.cart

import woowacourse.shopping.data.CartItemDao
import woowacourse.shopping.mapper.toCartItem
import woowacourse.shopping.mapper.toUiModel
import woowacourse.shopping.product.catalog.ProductUiModel

class CartItemRepository(
    private val dao: CartItemDao,
) {
    suspend fun getAllCartItem(): List<ProductUiModel> = dao.getAll().map { it.toUiModel() }

    suspend fun insertCartItem(product: ProductUiModel) {
        dao.insertCartItem(product.toCartItem())
    }

    suspend fun updateCartItem(product: ProductUiModel) {
        dao.updateQuantity(product.id, product.quantity)
    }

    suspend fun deleteCartItem(product: ProductUiModel) {
        dao.deleteCartItem(product.toCartItem())
    }

//    suspend fun findCartItem(product: ProductUiModel) {
//        dao.getCartItemByUid(product.toCartItem().uid)
//    }
}

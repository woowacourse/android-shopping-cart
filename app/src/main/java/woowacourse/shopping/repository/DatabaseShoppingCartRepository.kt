package woowacourse.shopping.repository

import woowacourse.shopping.model.Quantity
import woowacourse.shopping.model.ShoppingCartItem
import woowacourse.shopping.repository.dao.ShoppingCartItemDao
import woowacourse.shopping.repository.entity.ShoppingCartItemEntity

class DatabaseShoppingCartRepository(
    private val productRepository: ProductRepository,
    private val shoppingCartItemDao: ShoppingCartItemDao,
) : ShoppingCartRepository {

    override suspend fun addItemToCart(productId: String, amount: Int) {
        val cartItemEntity = shoppingCartItemDao.getCartItem(productId)
        if (cartItemEntity == null) {
            val product = productRepository.getProduct(productId) ?: return
            val shoppingCartItem =
                ShoppingCartItem(
                    quantity = Quantity(amount),
                    product = product,
                )
            shoppingCartItemDao.addCartItem(
                shoppingCartItemEntity = ShoppingCartItemEntity(
                    quantity = shoppingCartItem.quantity.value,
                    productId = product.id,
                )
            )
            return
        }

        val shoppingCartItem = cartItemEntity.toModel() ?: return

        shoppingCartItemDao.updateCartItem(
            shoppingCartItemEntity = cartItemEntity.copy(quantity = shoppingCartItem.increaseQuantity(amount).quantity.value)
        )
    }

    override suspend fun decreaseItemQuantity(productId: String, amount: Int) {
        val entity = shoppingCartItemDao.getCartItem(productId) ?: return
        val shoppingCartItem = entity.toModel()?.decreaseQuantity(amount)

        if (shoppingCartItem == null) {
            removeItemFromCart(productId)
            return
        }

        if (shoppingCartItem.isEmpty()) {
            removeItemFromCart(productId)
            return
        }

        shoppingCartItemDao.updateCartItem(
            shoppingCartItemEntity = entity.copy(quantity = shoppingCartItem.quantity.value)
        )
    }

    override suspend fun removeItemFromCart(productId: String) {
        shoppingCartItemDao.removeCartItem(productId)
    }

    override suspend fun getTotalSize(): Int = shoppingCartItemDao.getTotalSize()

    override suspend fun getTotalQuantity(): Int {
        return shoppingCartItemDao.getTotalQuantity()
    }

    override suspend fun getCartItem(productId: String): ShoppingCartItem? {
        val entity = shoppingCartItemDao.getCartItem(productId) ?: return null
        return entity.toModel()
    }

    override suspend fun getCartItems(
        offset: Int,
        size: Int,
    ): List<ShoppingCartItem> =
        shoppingCartItemDao.getItems(offset, size).mapNotNull {
            it.toModel()
        }

    private suspend fun ShoppingCartItemEntity.toModel(): ShoppingCartItem? {
        val product = productRepository.getProduct(productId) ?: return null
        return ShoppingCartItem(
            quantity = Quantity(quantity),
            product = product,
        )
    }
}

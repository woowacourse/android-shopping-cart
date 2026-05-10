package woowacourse.shopping.repository

import woowacourse.shopping.model.Quantity
import woowacourse.shopping.model.ShoppingCartItem
import woowacourse.shopping.repository.dao.ShoppingCartItemDao

class DatabaseShoppingCartRepository(
    private val productRepository: ProductRepository,
    private val shoppingCartItemDao: ShoppingCartItemDao,
) : ShoppingCartRepository {

    override suspend fun increaseItemQuantityByProductId(productId: String, quantity: Quantity) {
        val item = shoppingCartItemDao.getItemByProductId(productId)
        if (item == null) {
            shoppingCartItemDao.addCartItemByProductId(productId, quantity.value)
            return
        }
        shoppingCartItemDao.changeItemQuantity(productId, quantity.value)
    }

    override suspend fun getTotalSize(): Int = shoppingCartItemDao.getTotalSize()

    override suspend fun removeItem(productId: String) {
        shoppingCartItemDao.removeItem(productId)
    }

    override suspend fun decreaseItemQuantityByProductId(productId: String, quantity: Quantity) {
        shoppingCartItemDao.changeItemQuantity(productId, -quantity.value)
        shoppingCartItemDao.removeItemByProductIdIfQuantityIsZeroOrLess(productId)
    }

    override suspend fun getItemByProductId(productId: String): ShoppingCartItem? {
        val entity = shoppingCartItemDao.getItemByProductId(productId) ?: return null
        return ShoppingCartItem(
            id = entity.id.toString(),
            quantity = Quantity(entity.quantity),
            product = productRepository.getProduct(entity.productId) ?: throw IllegalArgumentException("상품을 찾을 수 없습니다"),
        )
    }

    override suspend fun getItems(
        offset: Int,
        size: Int,
    ): List<ShoppingCartItem> =
        shoppingCartItemDao.getItems(offset, size).map {
            val product = productRepository.getProduct(it.productId)
            ShoppingCartItem(
                quantity = Quantity(it.quantity),
                product = product ?: throw IllegalArgumentException("상품을 찾을 수 없습니다"),
                id = it.id.toString(),
            )
        }

    override suspend fun getTotalQuantity(): Int {
        return shoppingCartItemDao.getTotalQuantity()
    }
}

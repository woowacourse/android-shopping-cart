package woowacourse.shopping.repository

import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.model.ShoppingCartItem
import woowacourse.shopping.repository.dao.ShoppingCartItemDao

class DatabaseShoppingCartRepository(
    private val productRepository: ProductRepository,
    private val shoppingCartItemDao: ShoppingCartItemDao,
) : ShoppingCartRepository {
    override suspend fun add(product: Product) {
        shoppingCartItemDao.addOrIncreaseCartItem(product.id, 1)
    }

    override suspend fun getTotalSize(): Int = shoppingCartItemDao.getTotalSize()

    override suspend fun remove(shoppingCartItemId: String) {
        shoppingCartItemDao.removeOrDecreaseCartItem(shoppingCartItemId, 1)
    }

    override suspend fun getShoppingItem(shoppingCartItemId: String): ShoppingCartItem? {
        val entity = shoppingCartItemDao.getShoppingCartItemEntity(shoppingCartItemId) ?: return null
        return ShoppingCartItem(
            id = entity.id.toString(),
            quantity = Quantity(entity.quantity),
            product = productRepository.getProduct(entity.productId) ?: throw IllegalArgumentException("이미 삭제된 상품입니다"),
        )
    }

    override suspend fun getShoppingItems(
        offset: Int,
        size: Int,
    ): List<ShoppingCartItem> =
        shoppingCartItemDao.getShoppingCartItemEntities(offset, size).map {
            val product = productRepository.getProduct(it.productId)
            ShoppingCartItem(
                quantity = Quantity(it.quantity),
                product = product ?: throw IllegalArgumentException("Product not found"),
                id = it.id.toString(),
            )
        }
}

package woowacourse.shopping.data.shoppingcart

import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.ShoppingProduct
import kotlin.concurrent.thread

class ShoppingCartRepositoryImpl(
    private val dao: ShoppingCartDao,
) : ShoppingCartRepository {
    override fun getAll(onLoad: (Result<List<ShoppingProduct>?>) -> Unit) {
        { dao.getAll().toDomain() }.runAsync(onLoad)
    }

    override fun getAllSize(onLoad: (Result<Int>) -> Unit) {
        { dao.count() }.runAsync(onLoad)
    }

    override fun getPaged(
        limit: Int,
        offset: Int,
        onLoad: (productId: Result<List<ShoppingProduct>>) -> Unit,
    ) {
        { dao.getPaged(limit, offset).toDomain() }.runAsync(onLoad)
    }

    override fun insert(
        productId: Long,
        quantity: Int,
        onLoad: (Result<Unit>) -> Unit,
    ) {
        {
            val existing = dao.getByProductId(productId)
            if (existing != null) {
                dao.updateQuantity(productId, quantity)
            } else {
                dao.insert(ShoppingCartEntity(productId = productId, quantity = quantity))
            }
        }.runAsync(onLoad)
    }

    override fun increaseProduct(
        productId: Long,
        onLoad: (Result<Unit>) -> Unit,
    ) {
        {
            val existing = dao.getByProductId(productId)
            if (existing != null) {
                dao.increaseQuantity(productId)
            } else {
                dao.insert(ShoppingCartEntity(productId = productId, quantity = 1))
            }
        }.runAsync(onLoad)
    }

    override fun decreaseProduct(
        productId: Long,
        onLoad: (Result<Unit>) -> Unit,
    ) {
        {
            val currentQuantity = dao.getQuantity(productId)
            if (currentQuantity > 1) {
                dao.decreaseQuantity(productId)
            } else {
                dao.delete(productId)
            }
        }.runAsync(onLoad)
    }

    override fun get(
        productId: Long,
        onLoad: (Result<ShoppingProduct?>) -> Unit,
    ) {
        {
            dao.getByProductId(productId)?.toDomain()
        }.runAsync(onLoad)
    }

    override fun getQuantity(
        productId: Long,
        onLoad: (Result<Int>) -> Unit,
    ) {
        {
            dao.getQuantity(productId)
        }.runAsync(onLoad)
    }

    override fun delete(
        productId: Long,
        onLoad: (Result<Unit>) -> Unit,
    ) {
        { dao.delete(productId) }.runAsync(onLoad)
    }

    private inline fun <T> (() -> T).runAsync(crossinline onResult: (Result<T>) -> Unit) {
        thread {
            val result = runCatching(this)
            onResult(result)
        }
    }
}

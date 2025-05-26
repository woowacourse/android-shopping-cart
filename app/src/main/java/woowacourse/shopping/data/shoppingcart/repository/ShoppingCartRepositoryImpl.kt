package woowacourse.shopping.data.shoppingcart.repository

import woowacourse.shopping.data.shoppingcart.database.ShoppingCartDao
import woowacourse.shopping.data.shoppingcart.util.toDomain
import woowacourse.shopping.data.shoppingcart.util.toEntity
import woowacourse.shopping.domain.model.ShoppingCartItem
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import kotlin.concurrent.thread

class ShoppingCartRepositoryImpl(
    private val dao: ShoppingCartDao,
) : ShoppingCartRepository {
    override fun upsertItem(
        item: ShoppingCartItem,
        onResult: (Result<Unit>) -> Unit
    ) {
        doAsyncCatching(
            block = {
                val existing = dao.getItemById(item.goods.id)
                if (existing != null) {
                    dao.update(item.goods.id, item.quantity)
                } else {
                    dao.insert(item.toEntity())
                }
            },
            onResult = onResult,
        )
    }

    override fun addOrIncreaseItem(
        item: ShoppingCartItem,
        onResult: (Result<Unit>) -> Unit
    ) {
        doAsyncCatching(
            block = {
                val existing = dao.getItemById(item.goods.id)
                val newQuantity = (existing?.quantity ?: 0) + item.quantity
                if (existing != null) {
                    dao.update(item.goods.id, newQuantity)
                } else {
                    dao.insert(item.toEntity())
                }
            },
            onResult = onResult,
        )
    }

    override fun decreaseQuantity(
        item: ShoppingCartItem,
        onResult: (Result<Unit>) -> Unit
    ) {
        doAsyncCatching(
            block = {
                dao.update(item.goods.id, item.quantity)
            },
            onResult = onResult,
        )
    }

    override fun removeItem(
        shoppingCartItem: ShoppingCartItem,
        onResult: (Result<Unit>) -> Unit
    ) {
        doAsyncCatching(
            block = {
                dao.delete(shoppingCartItem.toEntity())
            },
            onResult = onResult,
        )
    }

    override fun getItem(
        id: Long,
        onResult: (Result<ShoppingCartItem?>) -> Unit
    ) {
        doAsyncCatching(
            block = {
                dao.getItemById(id)?.toDomain()
            },
            onResult = onResult,
        )
    }

    override fun getAllItems(onResult: (Result<List<ShoppingCartItem>>) -> Unit) {
        doAsyncCatching(
            block = {
                dao.getAll().map { it.toDomain() }
            },
            onResult = onResult
        )
    }

    override fun getPagedItems(
        page: Int,
        count: Int,
        onResult: (Result<List<ShoppingCartItem>>) -> Unit
    ) {
        doAsyncCatching(
            block = {
                val offset = page * count
                dao.getPage(offset, count).map { it.toDomain() }
            },
            onResult = onResult,
        )
    }

    override fun getTotalQuantity(onResult: (Result<Int>) -> Unit) {
        doAsyncCatching(
            block = {
                dao.getAll().count()
            },
            onResult = onResult,
        )
    }

    private inline fun <T> doAsyncCatching(
        crossinline block: () -> T,
        crossinline onResult: (Result<T>) -> Unit,
    ) {
        thread {
            val result = runCatching { block() }
            onResult(result)
        }
    }
}

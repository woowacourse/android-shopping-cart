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
    override fun addItem(
        item: ShoppingCartItem,
        onResult: (Result<Unit>) -> Unit
    ) {
        doAsyncCatching(
            block = {
                dao.upsertItem(item.toEntity())
            },
            onResult = onResult,
        )
    }

    override fun addItems(
        items: List<ShoppingCartItem>,
        onResult: (Result<Unit>) -> Unit
    ) {
        doAsyncCatching(
            block = {
                val entities = items.map { it.toEntity() }
                dao.upsertItems(entities)
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

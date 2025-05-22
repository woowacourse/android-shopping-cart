package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartLocalDataSource
import woowacourse.shopping.data.entity.CartEntity
import woowacourse.shopping.domain.CartEntry
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.concurrent.thread

class CartRepositoryImpl(
    private val cartLocalDataSource: CartLocalDataSource,
) : CartRepository {
    override fun insert(
        item: CartEntry,
        onResult: (Unit) -> Unit,
    ) {
        thread {
            onResult(cartLocalDataSource.insert(item.toEntity()))
        }
    }

    override fun getByIds(
        ids: List<Long>,
        onResult: (List<CartEntry>) -> Unit,
    ) {
        thread {
            onResult(cartLocalDataSource.getByIds(ids).filterNotNull().map { it.toEntry() })
        }
    }

    override fun getAll(onResult: (List<CartEntry>) -> Unit) {
        thread {
            cartLocalDataSource.getAll().map { it.toEntry() }
        }
    }

    override fun totalSize(onResult: (Int) -> Unit) {
        thread {
            onResult(cartLocalDataSource.totalSize())
        }
    }

    override fun deleteById(
        id: Long,
        onResult: (Unit) -> Unit,
    ) {
        thread {
            onResult(cartLocalDataSource.deleteById(id))
        }
    }

    override fun getPaged(
        offset: Int,
        limit: Int,
        onResult: (List<CartEntry>) -> Unit,
    ) {
        thread {
            onResult(cartLocalDataSource.getPaged(offset, limit).map { it.toEntry() })
        }
    }

    override fun hasOnlyPage(
        limit: Int,
        onResult: (Boolean) -> Unit,
    ) {
        thread {
            onResult(cartLocalDataSource.hasOnlyPage(limit))
        }
    }

    override fun hasNextPage(
        nextOffset: Int,
        limit: Int,
        onResult: (Boolean) -> Unit,
    ) {
        thread {
            onResult(cartLocalDataSource.getPaged(nextOffset, limit).isNotEmpty())
        }
    }

    private fun CartEntry.toEntity() = CartEntity(productId, quantity)

    private fun CartEntity.toEntry() = CartEntry(productId, quantity)
}

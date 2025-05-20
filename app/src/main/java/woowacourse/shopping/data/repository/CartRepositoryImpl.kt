package woowacourse.shopping.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import woowacourse.shopping.data.CartDatabase
import woowacourse.shopping.data.toDomain
import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.model.Goods
import kotlin.concurrent.thread

class CartRepositoryImpl(
    private val cartDatabase: CartDatabase,
) : CartRepository {
    override fun getAll(): LiveData<List<Goods>> =
        cartDatabase.cartDao().getAll().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun insert(goods: Goods) {
        thread {
            cartDatabase.cartDao().insertAll(goods.toEntity())
        }
    }

    override fun delete(goods: Goods) {
        thread {
            cartDatabase.cartDao().delete(goods.toEntity())
        }
    }

    override fun getPage(
        limit: Int,
        offset: Int,
    ): LiveData<List<Goods>> =
        cartDatabase.cartDao().getPage(limit, offset).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getAllItemsSize(): LiveData<Int> = cartDatabase.cartDao().getAllItemsSize()
}

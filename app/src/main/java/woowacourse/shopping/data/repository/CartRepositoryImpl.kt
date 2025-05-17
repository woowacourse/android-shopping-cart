package woowacourse.shopping.data.repository

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import woowacourse.shopping.data.ShoppingDatabase
import woowacourse.shopping.data.toDomain
import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.model.Goods
import kotlin.concurrent.thread

class CartRepositoryImpl(
    private val shoppingDatabase: ShoppingDatabase,
) : CartRepository {
    override fun getAll(): LiveData<List<Goods>> =
        shoppingDatabase.cartDao().getAll().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun insert(
        goods: Goods,
        onComplete: () -> Unit,
    ) {
        thread {
            shoppingDatabase.cartDao().insertAll(goods.toEntity())

            Handler(Looper.getMainLooper()).post {
                onComplete()
            }
        }
    }

    override fun delete(goods: Goods) {
        thread {
            shoppingDatabase.cartDao().delete(goods.toEntity())
        }
    }

    override fun getPage(
        limit: Int,
        offset: Int,
    ): LiveData<List<Goods>> =
        shoppingDatabase.cartDao().getPage(limit, offset).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getAllItemsSize(): LiveData<Int> = shoppingDatabase.cartDao().getAllItemsSize()
}

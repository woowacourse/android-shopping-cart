package woowacourse.shopping.data.repository

import android.os.Handler
import android.os.Looper
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

    override fun insert(
        goods: Goods,
        onComplete: () -> Unit,
    ) {
        thread {
            cartDatabase.cartDao().insertAll(goods.toEntity())

            Handler(Looper.getMainLooper()).post {
                onComplete()
            }
        }
    }
}

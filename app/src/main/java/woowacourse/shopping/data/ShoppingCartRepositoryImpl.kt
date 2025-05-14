package woowacourse.shopping.data

import android.content.Context
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.ShoppingProduct
import kotlin.concurrent.thread

class ShoppingCartRepositoryImpl(
    applicationContext: Context,
    private val dao: ShoppingCartDao =
        ShoppingCartDatabase.getDataBase(applicationContext).shoppingCartDao(),
) : ShoppingCartRepository {
    override fun getAll(): List<ShoppingProduct> {
        var result = listOf<ShoppingProduct>()
        thread {
            result = dao.getAll().toDomain()
        }.join()
        return result
    }

    override fun getPaged(
        limit: Int,
        offset: Int,
    ): List<ShoppingProduct> {
        var result = listOf<ShoppingProduct>()
        thread {
            result = dao.getPaged(limit, offset).toDomain()
        }.join()
        return result
    }

    override fun insert(productId: Long) {
        thread {
            dao.insert(ShoppingCartEntity(productId = productId))
        }.join()
    }

    override fun delete(shoppingCartId: Long) {
        thread {
            dao.delete(shoppingCartId)
        }.join()
    }
}

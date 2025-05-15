package woowacourse.shopping.data.shoppingcart

import android.content.Context
import woowacourse.shopping.data.ShoppingCartDatabase
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.ShoppingProduct
import woowacourse.shopping.view.PagedResult
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

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<ShoppingProduct> {
        var total = 0
        thread {
            total = dao.count()
        }.join()

        if (offset >= total) return PagedResult(emptyList(), false)

        val endIndex = (offset + limit).coerceAtMost(total)
        var items = listOf<ShoppingProduct>()
        thread {
            items = dao.getPaged(endIndex - offset, offset).toDomain()
        }.join()

        val hasNext = endIndex < total
        return PagedResult(items, hasNext)
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
